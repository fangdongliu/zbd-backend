package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.MapStudentCourse;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.*;
import cn.fdongl.point.mapper.MapCourseEvaluationMapper;
import cn.fdongl.point.mapper.MapStudentCourseMapper;
import cn.fdongl.point.mapper.MapStudentEvaluationMapper;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.util.AcademicYear;
import cn.fdongl.point.util.ExcelUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ClassPointServiceImpl implements ClassPointService {

    @Autowired
    private MapStudentEvaluationMapper mapStudentEvaluationMapper;

    @Autowired
    private SysIndexMapper sysIndexMapper;

    @Autowired
    private MapCourseEvaluationMapper mapCourseEvaluationMapper;
    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;

    @Autowired
    private MapStudentCourseMapper mapStudentCourseMapper;

    @Override
    public String savePoint(String classId, MultipartFile file) throws Exception {

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
        List<ExcelContent> list = new ArrayList<>();

        for (int t = 0; t < workbook.getNumberOfSheets(); t++) {
            sheet = workbook.getSheetAt(t);
            if (sheet == null) {
                continue;
            }
            String sheetName=sheet.getSheetName();
            int f=sheetName.indexOf("评价值");
            String grade=null;
            if(f==-1){
                //说明该sheet不是评价表
                continue;
            }else{
                 grade=sheetName.substring(0,4);//获取评价表中的级数
            }

            ExcelContent excelContent=new ExcelContent();
            excelContent.setSchoolYear(grade);
            List<Object> lo=new ArrayList<>();
            for (int j =0; j < sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                lo.add(li);
            }
            excelContent.setLi(lo);
            list.add(excelContent);
        }
        workbook.close();
        inputStream.close();

        for(int i=0;i<list.size();i++){
            //遍历每张sheet表
            ExcelContent excelContent=new ExcelContent();
            String grade=excelContent.getSchoolYear();//获取该张sheet表针对的级数
            List lo=excelContent.getLi();
            List<Object> first= (List<Object>) lo.get(0);
            int s=0;
            int v=0;
            for(int j=0;j<first.size();j++){
                HSSFCell cell1 = (HSSFCell) first.get(j);
                String val = cell1.getRichStringCellValue().getString();
                if("达成目标值".equals(val)){
                    s=j;
                }
                if("评价值".equals(val)){
                    v=j;
                }
            }
            List<Object> l=excelContent.getLi();
            for(int j=1;j<l.size();j++){
                //遍历每一行
                List<Object> lis= (List<Object>) l.get(j);
                HSSFCell cell2 = (HSSFCell) first.get(0);
                String name = cell2.getRichStringCellValue().getString();
                if(name!=null){
                    MapCourseEvaluation mapCourseEvaluation=new MapCourseEvaluation();
                    String[] n=name.split(" ");
                    if(n[0]!=null){
                        mapCourseEvaluation.setIndexNumber(n[0]);
                        SysIndex sysIndex=sysIndexMapper.selectByIdAndDate(n[0]);
                        mapCourseEvaluation.setIndexId(sysIndex.getId());
                        cell2= (HSSFCell) first.get(s);//目标值
                        String value=cell2.getRichStringCellValue().getString();
                        if(value==null){
                            //目标表值为空
                            return  grade+"级评价表中"+n[0]+"的目标值为空";
                        }
                        //目标表值不为空
                        mapCourseEvaluation.setIndexId(sysIndex.getId());
                        boolean flag=false;
                        while(j<l.size()){
                            //从下面的行中找评价值
                            List<Object> list2= (List<Object>) l.get(j);
                            HSSFCell cell1=(HSSFCell) list2.get(v);
                            String com=cell1.getRichStringCellValue().getString();
                            if(com!=null){
                                Double commentValue=Double.valueOf(com);
                                mapCourseEvaluation.setEvaluationValue(commentValue);
                                flag=true;
                                break;
                            }
                        }
                        if(flag){
                            return grade+"级评价表中"+n[0]+"的评价值为空";
                        }
                    }
                    mapCourseEvaluation.setStudentGrade(excelContent.getSchoolYear());//设置学生年级
                    mapCourseEvaluation.setId(IdGen.uuid());
                    mapCourseEvaluation.setCourseId(classId);
                    mapCourseEvaluationMapper.insertSelective(mapCourseEvaluation);
                }

            }
        }

        //将文件保存到服务器
        SysFile newFile=new SysFile();
        String id=IdGen.uuid();
        String path= new ApplicationHome(this.getClass()).getSource().getParentFile().getPath()+id;
        newFile.setFilePath(path);
        newFile.setFileName(file.getName());
        newFile.setId(id);
        if(file.getSize() != 0 && !"".equals(file.getName())){
            FileOutputStream fileOut=new FileOutputStream(path);
            fileOut.write(file.getBytes());
        }
        MapTeacherCourse mapTeacherCourse=mapTeacherCourseMapper.selectByPrimaryKey(classId);
        mapTeacherCourse.setFileId(id);
        mapTeacherCourseMapper.updateByPrimaryKeySelective(mapTeacherCourse);
        return null;
    }

    /**
     * step1：学生work_id + course_select_number 确定该学生所选的指定课程，即map_student_evaluation的id
     * step2：根据学生对于该课程的评价值插入数据库
     *
     * @author zm
     * @return void
     * @date 2019/9/8 21:48
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePoint(String courseSelectNumber, Map<String, Integer> data, String studentWorkId){
        // step1:
        MapStudentCourse mapStudentCourse = mapStudentCourseMapper.selectByUserWorkIdAndCourseSelectNumber(
                studentWorkId, courseSelectNumber);
        // step2:
        MapStudentEvaluation mapStudentEvaluation = new MapStudentEvaluation();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            mapStudentEvaluation.setId(IdGen.uuid());
            mapStudentEvaluation.setMapStudentCourseId(mapStudentCourse.getId());
            mapStudentEvaluation.setIndexId(entry.getKey());
            mapStudentEvaluation.setEvaluationValue(entry.getValue());

            mapStudentEvaluationMapper.insertSelective(mapStudentEvaluation);
        }
    }

    class ExcelContent{
        String schoolYear;
        List li;
        void setSchoolYear(String schoolYear){
            this.schoolYear=schoolYear;
        }
        String getSchoolYear(){
            return this.schoolYear;
        }
        void setLi(List li){
            this.li=li;
        }

        List getLi(){
            return li;
        }
    }
}
