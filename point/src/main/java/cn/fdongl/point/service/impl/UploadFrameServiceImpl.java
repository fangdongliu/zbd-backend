package cn.fdongl.point.service.impl;

import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.service.SysUserService;
import cn.fdongl.authority.util.IdGen;
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
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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


    @Override
    public String uploadProject(MultipartFile projectFile, HttpServletRequest request) throws IOException {
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
        String school=fileName.split("学院")[0]+"学院";//获取学院
        String[] v=fileName.split("-");
        String[] s=v[2].split("表");
        String grade=s[1].substring(0,4);
        List<SysFile> files= sysFileMapper.selectAllFile();
        MapCultivateFile mapCultivateFile=new MapCultivateFile();
        mapCultivateFile.setCollege(school);
        mapCultivateFile.setCultivateName(fileName);
        mapCultivateFile.setGrade(grade);
        String fileId=null;
        String path=null;
        for(int i=0;i<files.size();i++){
            SysFile sysFile=files.get(i);
            if(sysFile.getFileName().equals(fileName)){
                //还有已上传的文件名存在
                path=sysFile.getFilePath();
                fileId=sysFile.getId();
            }
        }
        if(path==null){
            //建立新的路径
            SysFile sysFile=new SysFile();
            String id=IdGen.uuid();
            path= new ApplicationHome(this.getClass()).getSource().getParentFile().getPath()+"\\uploads\\cultivatePlan\\" +id;
            sysFile.setId(id);
            sysFile.setFileName(fileName);
            sysFile.setFilePath(path);
            sysFileMapper.insertSelective(sysFile);
            fileId=id;
        }
        //将文件写到服务器中
        if(projectFile.getSize() != 0 && !"".equals(projectFile.getName())){
            FileOutputStream fileOut=new FileOutputStream(path);
            fileOut.write(projectFile.getBytes());
        }
        mapCultivateFile.setFileId(fileId);
        mapCultivateFileMapper.insertSelective(mapCultivateFile);
        System.out.println(path);

        return null;
    }


    @Override
    public String uploadClassPoint(MultipartFile classPointFile) throws IOException {
        List<SysIndex> indexList = new ArrayList<>();
        Sheet nowSheet = ExcelUtils.getSheet(classPointFile, 0);
        Row row = nowSheet.getRow(3);
        Iterator cells = row.cellIterator();
        List<Double> flagValues = new ArrayList<>();
        String msg = null;//返回的结果信息
        //创建指标点
        while (cells.hasNext()) {
            Cell cell = (Cell) cells.next();
            String val = cell.getStringCellValue();
            if (val != null) {
                int colNum = cell.getColumnIndex();
                String[] vals = val.split(" ");
                SysIndex sysIndex = new SysIndex();
                sysIndex.setIndexTitle(vals[0]);
                sysIndex.setId(IdGen.uuid());
                sysIndex.setIndexContent(vals[1]);
                indexList.set(colNum, sysIndex);
                flagValues.set(colNum, new Double(0));
            }
        }

        sysIndexMapper.insertList(indexList);

        List<MapCourseIndex> mapCourseIndices = new ArrayList<>();


        //课程和指标点对应
        for (int i = 4; i <= nowSheet.getLastRowNum(); i++) {
            Row r = nowSheet.getRow(i);//获取行元素
            Iterator cs = row.cellIterator();
            //遍历每一行的值
            int j = 0;
            String courseId = null;
            while (cs.hasNext()) {
                HSSFCell cell = (HSSFCell) cs.next();
                if (j == 0) {
                    //第一个元素表示课程代号
                    String courseNum = (String) ExcelUtils.getCellValue(cell);
                    if (courseNum == null) {
                        //课程代码为空
                        break;
                    } else {
                        courseId = sysCourseMapper.selectIbByNumber(courseNum);
                    }
                } else {
                    //后面的元素为指标值
                    Double indexValue = (Double) ExcelUtils.getCellValue(cell);
                    if (indexValue != null) {
                        //说明该指标值存在
                        int col = cell.getColumnIndex();//获取列号;
                        SysIndex sysIndex = indexList.get(col);//获取该列的指标值
                        MapCourseIndex mapCourseIndex = new MapCourseIndex();
                        mapCourseIndex.setId(IdGen.uuid());
                        mapCourseIndex.setIndexId(sysIndex.getId());
                        mapCourseIndex.setProportionValue(indexValue);
                        mapCourseIndex.setCourseId(courseId);

                        Double val = flagValues.get(col);
                        val += indexValue;
                        flagValues.set(col, val);
                        mapCourseIndices.add(mapCourseIndex);
                    }

                }
            }

        }

        mapCourseIndexMapper.insertList(mapCourseIndices);
        for (int i = 0; i < mapCourseIndices.size(); i++) {
            if (mapCourseIndices.get(i) != null) {
                Double d = flagValues.get(i);
                if (d - 1 > 0.00001) {
                    msg = "某指标点和的值不为1";
                }
            }
        }
        return msg;

    }

    /**
     * 上传教师信息
     *
     * @author zm
     * @param teacherFile
     * @return java.lang.String        
     * @date 2019/9/9 14:30
     **/
    @Override
    public String uploadTeacherInfo(MultipartFile teacherFile) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = teacherFile.getInputStream();
        // 获取文件名称
        String fileName = teacherFile.getOriginalFilename();
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
        HashMap<String, Integer> sysUserMap = sysUserService.getSysUserMap();
        //新建待插入的用户
        SysUser newTeacher = new SysUser();
        // 前两行是表头
        for (int i = 2; i < list.size(); i++) {
            //lo 是一行
            List<Object> lo = (List<Object>) list.get(i);
            //0行是工号
            HSSFCell workIdCell = (HSSFCell) lo.get(0);
            //已经有该教师-skip
            if (sysUserMap.get(workIdCell.getRichStringCellValue().getString()) != null){
                continue;
            }
            newTeacher.setId(IdGen.uuid());
            //2行是姓名
            HSSFCell realNameCell = (HSSFCell) lo.get(2);
            //3行是学院信息
            HSSFCell departmentCell = (HSSFCell) lo.get(3);

            newTeacher.setUserName(workIdCell.getRichStringCellValue().getString());
            newTeacher.setSecretePwd("123456");
            newTeacher.setWorkId(workIdCell.getRichStringCellValue().getString());
            newTeacher.setRealName(realNameCell.getRichStringCellValue().getString());
            newTeacher.setUserDepartment(departmentCell.getRichStringCellValue().getString());
            newTeacher.setUserType("teacher");

            sysUserMapper.insertSelective(newTeacher);
        }
        return "上传教师信息成功";
    }

    /**
     * 上传学生选课
     *
     * @param studentCourse
     * @return java.lang.String
     * @author zm
     * @date 2019/9/8 8:56
     **/
    @Transactional
    @Override
    public String uploadStudentCourse(MultipartFile studentCourse) throws IOException {
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
            return "请上传正确的表格文件";
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

        //新建待插入的学生(如果学号重复就不新增学生)
        SysUser newStudent = new SysUser();
        //新建student-course关联对象
        MapStudentCourse newStudentCourse = new MapStudentCourse();

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
        // 21列是选课课号
        HSSFCell courseSelectNumber = null;

        // 取出所有的用户形成HashMap
        HashMap<String, Integer> sysUserMap = sysUserService.getSysUserMap();

        // 第一行是表头
        for (int i = 1; i < list.size(); i++) {
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
                newStudent.setSecretePwd("123456");
                newStudent.setEducationSystem(Integer.parseInt(educationSystemCell.getRichStringCellValue().getString()));
                newStudent.setUserDepartment(userDepartmentCell.getRichStringCellValue().getString());
                newStudent.setTrainLevel(trainLevelCell.getRichStringCellValue().getString());
                newStudent.setClassName(classNameCell.getRichStringCellValue().getString());

                sysUserMapper.insertSelective(newStudent);
                // 记得添加新的map
                sysUserMap.put(workIdCell.getRichStringCellValue().getString(),1);
            }

            courseSemesterCell = (HSSFCell) lo.get(3);
            courseSelectNumber = (HSSFCell) lo.get(21);
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
            newStudentCourse.setCourseSemester(courseSemesterCell.getRichStringCellValue().getString());
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

            studentCourseMapper.insertSelective(newStudentCourse);
        }
        return "上传学生选课信息成功";
    }
}