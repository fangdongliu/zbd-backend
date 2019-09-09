package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.SysCourse;

import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.service.CourseUploadService;
import cn.fdongl.point.util.ExcelUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
public class CourseUploadServiceImp implements CourseUploadService {

    @Autowired
    private SysCourseMapper sysCourseMapper;

    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;

    //上传本学期计划执行列表
    @Transactional
    @Override
    public String uploadExecuteClass(MultipartFile file) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = file.getInputStream();
        // 获取文件名称
        String fileName = file.getOriginalFilename();
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
        List list = new ArrayList<>();



        for (int t = 0; t < workbook.getNumberOfSheets(); t++) {
            sheet = workbook.getSheetAt(t);

            if (sheet == null) {
                continue;
            }
            String schoolYear=null;
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                ExcelContent excelContent=new ExcelContent();
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    if(ExcelUtils.isMergedRegion(sheet,j,y)){
                        //是合并单元格
                        String val=ExcelUtils.getMergedRegionValue(sheet,j,y);
                        String v=val.substring(0,11);
                        schoolYear=v;
                    }else{
                        if(j>=3){
                            cell = row.getCell(y);
                            li.add(cell);
                        }

                    }
                }
                excelContent.setLi(li);
                excelContent.setSchoolYear(schoolYear);
                if(excelContent.getLi().size()!=0){
                    list.add(excelContent);
                }
            }

        }

        // 关闭流
        workbook.close();
        inputStream.close();
        List<SysCourse> courses = new ArrayList<>();
        List<String> courseNumList=sysCourseMapper.getAllCourseNum();
        Map<String,Integer> courseNumMap=new HashMap<>();
        for(int i=0;i<courseNumList.size();i++){
            courseNumMap.put(courseNumList.get(i),1);
        }

        for (int i = 0; i < list.size(); i++) {
            ExcelContent excelContent= (ExcelContent) list.get(i);
            List<Object> lo = excelContent.getLi();
            SysCourse sysCourse = new SysCourse();
            sysCourse.setId(IdGen.uuid());
            for (int j = 0; j < lo.size(); j++) {
                HSSFCell cell1 = (HSSFCell) lo.get(j);
                String val = cell1.getRichStringCellValue().getString();
                switch (j) {
                    //输入课程代码
                    case 0: {
                        sysCourse.setCourseNumber(val);
                        break;
                    }
                    case 1: {
                        sysCourse.setCourseName(val);
                        break;
                    }
                    case 2: {
                        Integer num = Integer.getInteger(val);
                        sysCourse.setTotalLen(num);
                        break;
                    }
                    case 3: {
                        Integer num = Integer.getInteger(val);
                        sysCourse.setSemesterLen(num);
                        break;
                    }
                    case 4: {
                        Double num = Double.valueOf(val);
                        sysCourse.setCourseCredit(num);
                        break;
                    }
                    case 5: {
                        sysCourse.setCourseType(val);
                        break;
                    }
                    case 6: {
                        sysCourse.setCourseAttribution(val);
                        break;
                    }
                    case 7:{
                        sysCourse.setAssessMethod(val);
                        break;
                    }
                    case 8: {
                        sysCourse.setCourseDepartment(val);
                        break;
                    }
                    case 9: {
                        sysCourse.setCourseRoute(val);
                        break;
                    }
                }
            }
            sysCourse.setCourseSemester(excelContent.getSchoolYear());
            Integer flag=courseNumMap.get(sysCourse.getCourseNumber());
            if(flag==null){
                sysCourseMapper.insertSelective(sysCourse);
            }
        }

        return null;

    }

    @Override
    public String uploadTeacherCourse(MultipartFile file) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = file.getInputStream();
        // 获取文件名称
        String fileName = file.getOriginalFilename();
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
        List list = new ArrayList<>();

        for (int t = 0; t < workbook.getNumberOfSheets(); t++) {
            sheet = workbook.getSheetAt(t);
            if (sheet == null) {
                continue;
            }
            for (int j =3; j < sheet.getLastRowNum(); j++) {
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
        workbook.close();
        inputStream.close();
        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = (List<Object>) list.get(i);
            MapTeacherCourse mapTeacherCourse=new MapTeacherCourse();
            mapTeacherCourse.setId(IdGen.uuid());
            for (int j = 0; j < lo.size(); j++) {
                HSSFCell cell1 = (HSSFCell) lo.get(j);
                String val = cell1.getRichStringCellValue().getString();
                switch(j){
                    case 0: {
                        //选课课号
                        String schoolYear=val.substring(1,12);
                        String s=val.substring(14);
                        String[] ls=s.split("-");//第一个是课号，第二个是老师工号
                        mapTeacherCourse.setCourseNumber(ls[0]);
                        mapTeacherCourse.setCourseSemester(schoolYear);
                        mapTeacherCourse.setTeacherWorkId(ls[1]);
                        break;
                    }
                    case 1: {
                        mapTeacherCourse.setCourseCampus(val);
                        break;
                    }
                    case 2: {
                        mapTeacherCourse.setCourseName(val);
                        break;
                    }
                    case 3: {
                        Double num = Double.valueOf(val);
                        mapTeacherCourse.setCourseCredit(num);
                        break;
                    }
                    case 4: {
                        mapTeacherCourse.setTeacherRealName(val);
                        break;
                    }
                    case 6: {
                        mapTeacherCourse.setCourseProperty(val);
                        break;
                    }
                    case 7: {
                        mapTeacherCourse.setCourseTestMethod(val);
                        break;
                    }
                    case 8:{
                        //选课人数
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setCourseElectNumber(num);
                        break;
                    }
                    case 9: {
                        //排课人数
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setCourseArrangeNumber(num);
                        break;
                    }
                    case 10: {
                        //讲课周次
                        mapTeacherCourse.setCourseTeacheWeek(val);
                        break;
                    }
                    case 12: {
                        //周学时
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setWeekLen(num);
                        break;
                    }
                    case 13: {
                        //安排学时
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setPlanLen(num);
                        break;
                    }
                    case 15: {
                        //讲课学时
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setTeachLen(num);
                        break;
                    }
                    case 16: {
                        //实践学时
                        Integer num=Integer.getInteger(val);
                        mapTeacherCourse.setPracticeLen(num);
                        break;
                    }
                    case 17:{
                        //班级名称
                        mapTeacherCourse.setCourseClass(val);
                        break;
                    }
                }
            }
            mapTeacherCourse.setStatus(3);
            mapTeacherCourseMapper.insertSelective(mapTeacherCourse);
        }

        return null;
    }


    class ExcelContent{
        String schoolYear;
        List<Object> li;
        void setSchoolYear(String schoolYear){
            this.schoolYear=schoolYear;
        }
        String getSchoolYear(){
            return this.schoolYear;
        }
        void setLi(List<Object> li){
            this.li=li;
        }

        List getLi(){
            return li;
        }
    }


}
