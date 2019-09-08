package cn.fdongl.point.service.impl;

import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.util.IdGen;
import cn.fdongl.authority.vo.SysUser;
import cn.fdongl.point.entity.*;
import cn.fdongl.point.mapper.*;
import cn.fdongl.point.service.UploadFrameService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



@Service
public class UploadFrameServiceImpl implements UploadFrameService {

    @Autowired
    private SysCourseMapper sysCourseMapper;
    @Autowired
    private SysIndexMapper sysIndexMapper;
    @Autowired
    private MapCourseIndexMapper mapCourseIndexMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
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
            path= request.getSession().getServletContext().getRealPath("/")+id;
            sysFile.setId(id);
            sysFile.setFileName(fileName);
            sysFile.setFilePath(path);
            sysFileMapper.insertSelective(sysFile);
            fileId=id;
        }
        //将文件写到服务器中
        if(projectFile.getSize() != 0 && !"".equals(projectFile.getName())){
            FileOutputStream fileOut=new FileOutputStream(path+projectFile.getOriginalFilename());
            fileOut.write(projectFile.getBytes());
        }
        mapCultivateFile.setFileId(fileId);
        mapCultivateFileMapper.insertSelective(mapCultivateFile);

        return null;
    }

    @Test
    public void test(){
        String val="软件学院2016版培养方案-10-指导性教学计划进程表2016级-20181008";
        String[] v=val.split("-");
        String[] s=v[2].split("表");
        System.out.println(s[1].substring(0,4));
    }

    @Override
    public String uploadClassPoint(MultipartFile classPointFile) throws IOException {
        List<SysIndex> indexList=new ArrayList<>();
        Sheet nowSheet=ExcelUtils.getSheet(classPointFile,0);
        Row row = nowSheet.getRow(3);
        Iterator cells = row.cellIterator();
        List<Double> flagValues=new ArrayList<>();
        String msg=null;//返回的结果信息
        //创建指标点
        while(cells.hasNext()){
            Cell cell = (Cell) cells.next();
            String val=cell.getStringCellValue();
            if(val!=null){
                int colNum=cell.getColumnIndex();
                String[] vals=val.split(" ");
                SysIndex sysIndex=new SysIndex();
                sysIndex.setIndexTitle(vals[0]);
                sysIndex.setId(IdGen.uuid());
                sysIndex.setIndexContent(vals[1]);
                indexList.set(colNum,sysIndex);
                flagValues.set(colNum, new Double(0));
            }
        }

        sysIndexMapper.insertList(indexList);

        List<MapCourseIndex> mapCourseIndices=new ArrayList<>();


        //课程和指标点对应
        for(int i= 4;i<=nowSheet.getLastRowNum();i++){
            Row r=nowSheet.getRow(i);//获取行元素
            Iterator cs = row.cellIterator();
            //遍历每一行的值
            int j=0;
            String courseId=null;
            while(cs.hasNext()){
                HSSFCell cell = (HSSFCell) cs.next();
                if(j==0){
                    //第一个元素表示课程代号
                    String courseNum= (String) ExcelUtils.getCellValue(cell);
                    if(courseNum==null){
                        //课程代码为空
                        break;
                    }else{
                        courseId=sysCourseMapper.selectIbByNumber(courseNum);
                    }
                }else{
                    //后面的元素为指标值
                    Double indexValue= (Double) ExcelUtils.getCellValue(cell);
                    if(indexValue!=null){
                        //说明该指标值存在
                        int col=cell.getColumnIndex();//获取列号;
                        SysIndex sysIndex=indexList.get(col);//获取该列的指标值
                        MapCourseIndex mapCourseIndex=new MapCourseIndex();
                        mapCourseIndex.setId(IdGen.uuid());
                        mapCourseIndex.setIndexId(sysIndex.getId());
                        mapCourseIndex.setProportionValue(indexValue);
                        mapCourseIndex.setCourseId(courseId);

                        Double val=flagValues.get(col);
                        val+=indexValue;
                        flagValues.set(col,val);
                        mapCourseIndices.add(mapCourseIndex);
                    }

                }
            }

        }

        mapCourseIndexMapper.insertList(mapCourseIndices);
        for(int i=0;i<mapCourseIndices.size();i++){
            if(mapCourseIndices.get(i)!=null){
                Double d=flagValues.get(i);
                if(d-1>0.00001){
                    msg="某指标点和的值不为1";
                }
            }
        }
        return msg;

    }

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
        // 如果上传为非excel文件，返回
        if (workbook == null) {
            return "请上传文件";
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

        //新建待插入的用户
        SysUser newTeacher = new SysUser();

        // 前两行是表头
        for (int i = 2; i < list.size(); i++) {
            //lo 是一行
            List<Object> lo = (List<Object>) list.get(i);
            newTeacher.setId(IdGen.uuid());
            //0行是工号
            HSSFCell workIdCell = (HSSFCell) lo.get(0);
            HSSFCell realNameCell = (HSSFCell) lo.get(2);
            HSSFCell departmentCell = (HSSFCell) lo.get(3);
            newTeacher.setUserName(workIdCell.getRichStringCellValue().getString());
            newTeacher.setSecretePwd("123456");
            newTeacher.setWorkId(workIdCell.getRichStringCellValue().getString());
            //2行是姓名
            newTeacher.setRealName(realNameCell.getRichStringCellValue().getString());
            //3行是学院信息
            newTeacher.setUserDepartment(departmentCell.getRichStringCellValue().getString());
            //4行是教师身份
            newTeacher.setUserType("teacher");

            sysUserMapper.insertSelective(newTeacher);
        }
        return "上传成功";
    }
}