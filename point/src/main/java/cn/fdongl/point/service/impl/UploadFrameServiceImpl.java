package cn.fdongl.point.service.impl;

import cn.fdongl.authority.mapper.MapUserRoleMapper;
import cn.fdongl.authority.mapper.SysRoleMapper;
import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.service.MapUtilService;
import cn.fdongl.authority.service.SysUserService;
import cn.fdongl.authority.util.IdGen;
import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.authority.vo.MapUserRole;
import cn.fdongl.authority.vo.SysUser;
import cn.fdongl.point.entity.MapStudentCourse;
import cn.fdongl.point.mapper.MapCourseIndexMapper;
import cn.fdongl.point.mapper.MapStudentCourseMapper;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.entity.MapCourseIndex;
import cn.fdongl.point.entity.SysCourse;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.entity.*;
import cn.fdongl.point.mapper.*;
import cn.fdongl.point.service.UploadFrameService;
import cn.fdongl.point.util.DateUtils;
import cn.fdongl.point.util.ExcelUtils;
import cn.fdongl.point.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;



import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;


@Service
public class UploadFrameServiceImpl implements UploadFrameService {

    @Autowired
    private SysCourseMapper sysCourseMapper;
    @Autowired
    private SysIndexMapper sysIndexMapper;
    @Autowired
    private MapCourseIndexMapper mapCourseIndexMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private MapStudentCourseMapper studentCourseMapper;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private MapCultivateFileMapper mapCultivateFileMapper;
    @Autowired
    private MapUtilService mapUtilService;
    @Autowired
    private MapDepartmentIndexMapper mapDepartmentIndexMapper;
    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;
    @Autowired
    private UploadStatusMapper uploadStatusMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public String uploadProject(MultipartFile projectFile, HttpServletRequest request, JwtUser user) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = projectFile.getInputStream();
        // 获取文件名称
        String fileName = projectFile.getOriginalFilename();
        // init工作簿
        Workbook workbook = null;
        // 获取文件后缀
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        // 根据不同后缀init不同的类，是xls还是xlsx
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = null;
            return "请上传正确的表格文件";
        }
        // 如果上传为非excel文件，返回
        if (workbook == null) {
            return "请上传文件";
        }

        // 关闭流
        workbook.close();
        inputStream.close();
        String school = fileName.split("学院")[0] + "学院";//获取学院
        String[] v = fileName.split("-");
        String[] s = v[2].split("表");
        String grade = s[1].substring(0, 4);
        List<SysFile> files = sysFileMapper.selectAllFile();
        MapCultivateFile mapCultivateFile = new MapCultivateFile();
        mapCultivateFile.setCollege(school);
        mapCultivateFile.setCultivateName(fileName);
        mapCultivateFile.setGrade(grade);
        String fileId = null;
        String path = null;
        for (int i = 0; i < files.size(); i++) {
            SysFile sysFile = files.get(i);
            if (sysFile.getFileName().equals(fileName)) {
                //还有已上传的文件名存在
                path = sysFile.getFilePath();
                fileId = sysFile.getId();
            }
        }
        if (path == null) {
            //建立新的路径
            SysFile sysFile = new SysFile();
            String id = IdGen.uuid();
            path = new ApplicationHome(this.getClass()).getSource().getParentFile().getPath() + "\\uploads\\cultivatePlan\\" + id;
            sysFile.setId(id);
            sysFile.setFileName(fileName);
            sysFile.setFilePath(path);
            sysFile.setCreateUserId(user.getId());
            sysFile.setModifyDate(new Date());
            sysFileMapper.insertSelective(sysFile);
            fileId = id;
        }
        //将文件写到服务器中
        if (projectFile.getSize() != 0 && !"".equals(projectFile.getName())) {
            FileOutputStream fileOut = new FileOutputStream(path);
            fileOut.write(projectFile.getBytes());
        }
        mapCultivateFile.setFileId(fileId);
        mapCultivateFileMapper.insertSelective(mapCultivateFile);
        System.out.println(path);


        return null;
    }


    /**
     * 处理上传培养矩阵
     *
     * @param cultivateMatrix 所上传的培养矩阵
     * @return void
     * @author zm
     * @date 2019/9/9 19:50
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadCultivateMatrix(MultipartFile cultivateMatrix,JwtUser user) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = cultivateMatrix.getInputStream();
        // 获取文件名称
        String fileName = cultivateMatrix.getOriginalFilename();
        // init工作簿
        Workbook workbook = null;
        // 获取文件后缀
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        // 根据不同后缀init不同的类，是xls还是xlsx
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = null;
            return;
        }
        // init
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 定义读取的容器 行集合
        List list = new ArrayList<>();
        // 循环 sheet 这里仅仅处理第三个sheet
        int sheetNum = workbook.getNumberOfSheets();
        // 设置 sheet 为第三个sheet
        sheet = workbook.getSheetAt(sheetNum - 1);
        // 循环 sheet
        for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            if (row == null || row.getFirstCellNum() == j) {
                continue;
            }
            List<Object> li = new ArrayList<>();
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                li.add(cell);
            }
            list.add(li);
        }
        // 关闭流
        workbook.close();
        inputStream.close();
        // 0行 0行的列1是 矩阵名称
        List<Object> zeroLo = (List<Object>) list.get(0);
        // 获取矩阵名称 e.g. 北京理工软件工程专业毕业要求指标点职称课程关联矩阵
        String matrixTitle = ExcelUtils.getMergedRegionValue(sheet, 0, 0);
        // 根据矩阵 title 获取专业名称
        String majority = StringUtils.getMajority(matrixTitle);
        // 截取 学院 + 专业 + 年级
        String department = StringUtils.getDepartment(fileName);
        String grade = StringUtils.getGrade(fileName);
        // 获取当前是第几期
        String dictTypeId = sysDictTypeMapper.selectIdByTypeName("培养矩阵期数");
        sysDictMapper.selectByPrimaryKey(dictTypeId);
        // 0行 指标点大类(因为第0行是合并的单元格，所以原第1行世纪是第0行，下同)
        List<Object> oneLo = (List<Object>) list.get(0);
        // 1行 指标点大类的说明
        List<Object> twoLo = (List<Object>) list.get(1);
        // 2行 指标点小类
        List<Object> threeLo = (List<Object>) list.get(2);
        XSSFCell threeRowSingleCell;

        // 新建indexList用来存储所有的指标点小项(下标对应的是excel的列，后续课程匹配指标点时候用到)
        List<SysIndex> sysIndexList = new ArrayList<>();
        // 新建关联用于插入
        MapDepartmentIndex mapDepartmentIndex = new MapDepartmentIndex();
        mapDepartmentIndex.setDepartmentName(department);
        mapDepartmentIndex.setMajorityName(majority);
        mapDepartmentIndex.setStudentGrade(grade);
        mapDepartmentIndex.setPeriod(sysDictMapper.selectRecentSort());
        mapDepartmentIndex.setCreateDate(DateUtils.getNowDate());
        mapDepartmentIndex.setModifyDate(DateUtils.getNowDate());
        // 遍历3行(第四行)
        for (int i = 0; i < threeLo.size(); i++) {
            threeRowSingleCell = (XSSFCell) threeLo.get(i);
            String cellValue = (String) ExcelUtils.getJavaValue(threeRowSingleCell);
            //结尾标志
            if ("所有支撑的指标点".equals(cellValue)) {
                break;
            }
            // 为空则在 sysIndexList 中插入null 不为空则插入有内容的 SysIndex
            if (cellValue == null || "".equals(cellValue)) {
                sysIndexList.add(null);
            } else {
                // 新建 SysIndex 用于插入
                SysIndex tmpIndex = new SysIndex();
                tmpIndex.setCreateDate(DateUtils.getNowDate());
                tmpIndex.setModifyDate(DateUtils.getNowDate());
                // 指标点小项的编号
                tmpIndex.setIndexNumber(cellValue.substring(0, cellValue.indexOf(" ")));
                // 指标点小项的说明
                tmpIndex.setIndexContent(cellValue.substring(cellValue.indexOf(" ")));
                // 指标点大项-存在title中
                tmpIndex.setIndexTitle(cellValue.substring(0,cellValue.indexOf(".")));
                // 加入指标点小项的list中
                sysIndexList.add(tmpIndex);
                // 插入指标点小项目
                sysIndexMapper.insertSelective(tmpIndex);
                // 插入关联
                mapDepartmentIndex.setUUId();
                mapDepartmentIndex.setIndexId(tmpIndex.getId());
                mapDepartmentIndexMapper.insertSelective(mapDepartmentIndex);
            }
        }

        // 首先更新字典项中的period信息
        sysDictMapper.periodAddOne();

        // 获取当前是第几期
        int pr = sysDictMapper.selectRecentSort();

        // 从第3行开始就是课程行
        for (int i = 3; i < list.size() - 2; i++) {
            List<Object> tmpLo = (List<Object>) list.get(i);
            // 0 列是课程编号
            String courseNumber = (String) ExcelUtils.getJavaValue((XSSFCell) tmpLo.get(0));
            // 1 列是课程名称
            String courseName = (String) ExcelUtils.getJavaValue((XSSFCell) tmpLo.get(1));
            // 根据是第几列来从sysIndexList取出对应的指标点小项目 id 和 number
            for (int j = 2; j < tmpLo.size() - 1; j++) {
                String singleColumnValue = (String) ExcelUtils.getJavaValue((XSSFCell) tmpLo.get(j));
                if (singleColumnValue == null || "".equals(singleColumnValue)) {
                    continue;
                }
                // 新建课程-指标点关联项用于插入
                MapCourseIndex mapCourseIndex = new MapCourseIndex();
                // 设置课程编号
                mapCourseIndex.setCourseNumber(courseNumber);
                mapCourseIndex.setSchoolGrade(grade);
                mapCourseIndex.setCreateDate(DateUtils.getNowDate());
                mapCourseIndex.setModifyDate(DateUtils.getNowDate());
                mapCourseIndex.setIndexNumber(sysIndexList.get(j).getIndexNumber());
                mapCourseIndex.setIndexId(sysIndexList.get(j).getId());
                mapCourseIndex.setProportionValue(Double.valueOf(singleColumnValue));
                mapCourseIndexMapper.insertSelective(mapCourseIndex);
            }
            System.out.println("第" + i + "行完成");
        }
        // 课程行后两行是统计行(第一列是空，暂不处理)
        //将上传文件保存到服务器
        SysFile sysFile=new SysFile();
        String path=  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"/cultivateMatrix";
        sysFile.setId(IdGen.uuid());
        sysFile.setFileName(cultivateMatrix.getOriginalFilename());
        sysFile.setStatus(2);
        sysFile.setFilePath(path);
        sysFile.setCreateUserId(user.getId());
        sysFile.setModifyDate(new Date());
        sysFileMapper.insertSelective(sysFile);
        File dest = new File(sysFile.getFilePath()+"/"+fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }

        cultivateMatrix.transferTo(dest);// 文件写入
    }

    /**
     * 上传教师信息
     *
     * @param teacherFile
     * @return java.lang.String
     * @author zm
     * @date 2019/9/9 14:30
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadTeacherInfo(MultipartFile teacherFile,JwtUser user) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = teacherFile.getInputStream();
        // 获取文件名称
        String fileName = teacherFile.getOriginalFilename();
        // init工作簿
        Workbook workbook = null;
        // 获取文件后缀
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        String encodedPwd = passwordEncoder.encode("123456");
        // 根据不同后缀init不同的类，是xls还是xlsx
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = null;
            return;
        }
        // init
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 定义读取的容器 行集合
        List list = new ArrayList<>();
        //循环 sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        // 关闭流
        workbook.close();
        inputStream.close();
        //取出所有的系统用户形成HashMap
//        HashMap<String, Integer> sysUserMap = sysUserService.getSysUserMap();
        //新建待插入的用户

        List<SysUser> users = new ArrayList<>();
        // 前两行是表头
        for (int i = 2; i < list.size(); i++) {
            //lo 是一行
            SysUser newTeacher = new SysUser();
            List<Object> lo = (List<Object>) list.get(i);
            //0行是工号
            HSSFCell workIdCell = (HSSFCell) lo.get(0);
            //已经有该教师-skip
//            if (sysUserMap.get(workIdCell.getRichStringCellValue().getString()) != null) {
//                continue;
//            }
            newTeacher.setId(IdGen.uuid());
            //2行是姓名
            HSSFCell realNameCell = (HSSFCell) lo.get(2);
            //3行是学院信息
            HSSFCell departmentCell = (HSSFCell) lo.get(3);

            newTeacher.setUserName(workIdCell.getRichStringCellValue().getString());
            newTeacher.setSecretePwd(encodedPwd);
            newTeacher.setWorkId(workIdCell.getRichStringCellValue().getString());
            newTeacher.setRealName(realNameCell.getRichStringCellValue().getString());
            newTeacher.setUserDepartment(departmentCell.getRichStringCellValue().getString());
            newTeacher.setUserType("teacher");

            //用户赋角色(teacher)
//            mapUtilService.addNewRoleMap(newTeacher.getId(), "teacher");
            //插入新的教师
            users.add(newTeacher);
            if(users.size()>400){
//                mapUtilService.addNewRoleMapBatch(users,"teacher");
                sysUserMapper.insertBatch(users);
                users.clear();
            }
//            sysUserMapper.insertSelective(newTeacher);
        }
        if(users.size()>0){
//            mapUtilService.addNewRoleMapBatch(users,"teacher");
            sysUserMapper.insertBatch(users);
//            users.clear();
        }

        //将文件写入服务器
        SysFile sysFile=new SysFile();
        String path=  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"/teacherInfo";
        sysFile.setId(IdGen.uuid());
        sysFile.setFileName(teacherFile.getOriginalFilename());
        sysFile.setStatus(3);
        sysFile.setFilePath(path);
        sysFile.setCreateUserId(user.getId());
        sysFile.setModifyDate(new Date());
        sysFileMapper.insertSelective(sysFile);
        File dest = new File(sysFile.getFilePath()+"/"+fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }

        teacherFile.transferTo(dest);// 文件写入
    }

    /**
     * 上传学生选课
     *
     * @param studentCourse
     * @return java.lang.String
     * @author zm
     * @date 2019/9/8 8:56
     **/
//    @Transactional(rollbackFor = Exception.class)
    @Override
//    @Async
    public void uploadStudentCourse(MultipartFile studentCourse,JwtUser user,String statusId) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = studentCourse.getInputStream();
        // 获取文件名称
        String fileName = studentCourse.getOriginalFilename();
        // init工作簿
        Workbook workbook;
        // 获取文件后缀
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        // 根据不同后缀init不同的类，是xls还是xlsx
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = null;
            return;
        }
        // init
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 定义读取的容器 行集合
        List list = new ArrayList<>();
        uploadStatusMapper.insert(statusId,0);
        //循环 sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        // 关闭流
        workbook.close();
        inputStream.close();

        // 获取此时的指标点期数
        int pr = sysDictMapper.selectRecentSort();

        //新建待插入的学生(如果学号重复就不新增学生)
        //新建student-course关联对象
        List<SysUser> users = new ArrayList<>();
        List<MapStudentCourse>mapStudentCourseList = new ArrayList<>();
        uploadStatusMapper.update(statusId,-1);
        // 0列是学号
        HSSFCell workIdCell;
        // 1列是姓名(realName)
        HSSFCell realNameCell;
        // 2列是学制(educationSystem)
        HSSFCell educationSystemCell;
        // 4列是上课院系
        HSSFCell userDepartmentCell;
        // 5列是培养层次
        HSSFCell trainLevelCell;
        // 6列是班级名称
        HSSFCell classNameCell;

        // 3列是开课学期
        HSSFCell courseSemesterCell = null;
        // 7列是课程编号
        HSSFCell courseNumberCell = null;
        // 8列是课程名称
        HSSFCell courseNameCell = null;
        // 9列是总成绩(double类型)
        HSSFCell totalGradeCell = null;
        // 10列是成绩标识(gradeSign)
        HSSFCell gradeSignCell = null;
        // 11列是课程性质
        HSSFCell courseNatureCell = null;
        // 12列是课程属性
        HSSFCell coursePropertyCell = null;
        // 13列是课程归属
        HSSFCell courseAscriptionCell = null;
        // 14列是课程种类
        HSSFCell courseKindCell = null;
        // 15列是学时
        HSSFCell courseHourCell = null;
        // 16列是学分
        HSSFCell courseCreditCell = null;
        // 17列是开课单位
        HSSFCell courseDepartmentCell = null;
        // 18列是录入人
        HSSFCell inputUserNameCell = null;
        // 19列是考试性质
        HSSFCell examNatureCell = null;
        // 20列是补重学期
        HSSFCell supplementRepeatCell = null;
        // 24列是选课课号
        HSSFCell courseSelectNumber = null;

        // 取出所有的用户形成HashMap
        HashMap<String, Integer> sysUserMap = new HashMap<>();
        String encodedPwd = passwordEncoder.encode("123456");
        // 第一行是表头
        for (int i = 1; i < list.size(); i++) {
            SysUser newStudent = new SysUser();
            // lo是一行
            List<Object> lo = (List<Object>) list.get(i);

            // 0列是工号
            workIdCell = (HSSFCell) lo.get(0);

            // 之前没有该学生，需要新增
            if (sysUserMap.get(workIdCell.getRichStringCellValue().getString()) == null) {
                realNameCell = (HSSFCell) lo.get(1);
                educationSystemCell = (HSSFCell) lo.get(2);
                userDepartmentCell = (HSSFCell) lo.get(4);
                trainLevelCell = (HSSFCell) lo.get(5);
                classNameCell = (HSSFCell) lo.get(6);

                newStudent.setUUId();
                newStudent.setUserName(workIdCell.getRichStringCellValue().getString());
                newStudent.setWorkId(workIdCell.getRichStringCellValue().getString());
                newStudent.setRealName(realNameCell.getRichStringCellValue().getString());
                newStudent.setUserType("student");
                newStudent.setSecretePwd(encodedPwd);
                newStudent.setEducationSystem(Integer.parseInt(educationSystemCell.getRichStringCellValue().getString()));
                newStudent.setUserDepartment(userDepartmentCell.getRichStringCellValue().getString());
                newStudent.setTrainLevel(trainLevelCell.getRichStringCellValue().getString());
                newStudent.setClassName(classNameCell.getRichStringCellValue().getString());

                users.add(newStudent);
                if(users.size() == 400){
                    sysUserMapper.insertBatch(users);
//                    mapUtilService.addNewRoleMapBatch(users,"student");
                    users.clear();
                }

                // 用户赋角色(student)
//                mapUtilService.addNewRoleMap(newStudent.getId(), "student");
//                // 插入新的学生
//                sysUserMapper.insertSelective(newStudent);
                // 记得添加新的map
                sysUserMap.put(workIdCell.getRichStringCellValue().getString(), 1);
            }
            MapStudentCourse newStudentCourse = new MapStudentCourse();

            courseSemesterCell = (HSSFCell) lo.get(3);
            courseSelectNumber = (HSSFCell) lo.get(24);
            courseNumberCell = (HSSFCell) lo.get(7);
            courseNameCell = (HSSFCell) lo.get(8);
            totalGradeCell = (HSSFCell) lo.get(9);
            gradeSignCell = (HSSFCell) lo.get(10);
            courseNatureCell = (HSSFCell) lo.get(11);
            coursePropertyCell = (HSSFCell) lo.get(12);
            courseAscriptionCell = (HSSFCell) lo.get(13);
            courseKindCell = (HSSFCell) lo.get(14);
            courseHourCell = (HSSFCell) lo.get(15);
            courseCreditCell = (HSSFCell) lo.get(16);
            courseDepartmentCell = (HSSFCell) lo.get(17);
            inputUserNameCell = (HSSFCell) lo.get(18);
            examNatureCell = (HSSFCell) lo.get(19);
            supplementRepeatCell = (HSSFCell) lo.get(20);

            newStudentCourse.setUUId();
            newStudentCourse.setUserWorkId(workIdCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseSemester(courseSemesterCell.getRichStringCellValue().getString().substring(0,9));
            newStudentCourse.setCourseNumber(courseNumberCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseSelectNumber(courseSelectNumber.getRichStringCellValue().getString());
            newStudentCourse.setCourseName(courseNameCell.getRichStringCellValue().getString());
            newStudentCourse.setTotalGrade(totalGradeCell.getRichStringCellValue().getString());
            newStudentCourse.setGradeSign(gradeSignCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseNature(courseNatureCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseProperty(coursePropertyCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseAscription(courseAscriptionCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseProperty(coursePropertyCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseKind(courseKindCell.getRichStringCellValue().getString());
            newStudentCourse.setCourseCredit(Double.parseDouble(courseCreditCell.getRichStringCellValue().getString()));
            newStudentCourse.setCourseDepartment(courseDepartmentCell.getRichStringCellValue().getString());
            newStudentCourse.setInputUserName(inputUserNameCell.getRichStringCellValue().getString());
            newStudentCourse.setExamNature(examNatureCell.getRichStringCellValue().getString());
            newStudentCourse.setSupplementRepeatSemester(supplementRepeatCell.getRichStringCellValue().getString());
            //设置指标点期数
            newStudentCourse.setStatus(pr);

            mapStudentCourseList.add(newStudentCourse);
            if(i>=9600){
                System.out.println("9600");
            }
            if(mapStudentCourseList.size()>=400){
                studentCourseMapper.insertBatch(mapStudentCourseList);
                uploadStatusMapper.update(statusId,i);
                mapStudentCourseList.clear();
            }

            //studentCourseMapper.insertSelective(newStudentCourse);
        }
        if(users.size()>0){
            sysUserMapper.insertBatch(users);
//            mapUtilService.addNewRoleMapBatch(users,"student");
        }
        if(mapStudentCourseList.size()>0){
            studentCourseMapper.insertBatch(mapStudentCourseList);
        }
        uploadStatusMapper.update(statusId,-2);
        inputStream.close();
        SysFile sysFile=new SysFile();
        String path=  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"/studentInfo";
        sysFile.setId(IdGen.uuid());
        sysFile.setFileName(studentCourse.getOriginalFilename());
        sysFile.setStatus(6);
        sysFile.setFilePath(path);
        sysFile.setCreateUserId(user.getId());
        sysFile.setModifyDate(new Date());
        sysFileMapper.insertSelective(sysFile);
        File dest = new File(sysFile.getFilePath()+"/"+fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }
        studentCourse.transferTo(dest);// 文件写入
    }
}