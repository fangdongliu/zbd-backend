package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.mapper.MapCourseEvaluationMapper;
import cn.fdongl.point.mapper.MapStudentEvaluationMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.entity.MapCourseEvaluation;
import cn.fdongl.point.entity.MapStudentEvaluation;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        List list = new ArrayList<>();

        for (int t = 0; t < workbook.getNumberOfSheets(); t++) {
            sheet = workbook.getSheetAt(t);
            if (sheet == null) {
                continue;
            }
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
                list.add(li);
            }

        }
        workbook.close();
        inputStream.close();

        List<Object> f= (List<Object>) list.get(0);
        int s=0;
        int v=0;
        for(int j=0;j<f.size();j++){
            String val= (String) f.get(j);
            if("达成目标值".equals(val)){
                s=j;
            }
            if("评价值".equals(val)){
                v=j;
            }
        }
        for(int j=0;j<list.size();j++){
            List<Object> lo= (List<Object>) list.get(j);
            String name= (String) lo.get(j);
            if(name!=null){
                MapTeacherCourse mapTeacherCourse=new MapTeacherCourse();
                String[] n=name.split(" ");
                mapTeacherCourse.setCourseName(n[0]);

            }
        }

        return null;
    }


    @Override
    public void savePoint(String classId, Map<String, Integer> data,String studentId) {
        List<MapStudentEvaluation> mapStudentEvaluations=new ArrayList<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            MapStudentEvaluation mapStudentEvaluation=new MapStudentEvaluation();
            mapStudentEvaluation.setId(IdGen.uuid());
            mapStudentEvaluation.setIndexNumber(entry.getKey());
            mapStudentEvaluation.setCommentValue(entry.getValue());
            mapStudentEvaluation.setWorkId(studentId);
            mapStudentEvaluation.setCourseSelectNumber(classId);
            mapStudentEvaluations.add(mapStudentEvaluation);
            mapStudentEvaluation.setIndexId(sysIndexMapper.selectByIdAndDate(entry.getKey()).getId());
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        mapStudentEvaluationMapper.insertList(mapStudentEvaluations);
    }


    /**
     * step1：学生id + course_select_number 确定该学生所选的指定课程，即map_student_evaluation的id
     * step2：根据学生对于该课程的评价值插入数据库
     *
     * @author zm
     * @param studentId 学生的主键id
     * @param courseSelectNumber 选课课号 course_select_number
     * @param evaluationArray 评分数组(下标0-11代表12个指标点)
     * @return void
     * @date 2019/9/8 21:48
     **/
    public void studentEvaluateCourse(String studentId, String courseSelectNumber, Array evaluationArray){

    }
}
