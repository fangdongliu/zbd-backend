package cn.fdongl.point.service.impl;

import cn.fdongl.point.dao.MapCourseIndexMapper;
import cn.fdongl.point.dao.MapStudentEvaluationMapper;
import cn.fdongl.point.entity.MapCourseIndex;
import cn.fdongl.point.entity.MapStudentEvaluation;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.util.AcademicYear;
import cn.fdongl.point.util.ExcelUtils;

import cn.fdongl.point.util.IdGen;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ClassPointServiceImpl implements ClassPointService {


    @Autowired
    private MapCourseIndexMapper mapCourseIndexMapper;

    @Autowired
    private MapStudentEvaluationMapper mapStudentEvaluationMapper;


    @Override
    public void savePoint(String classId, MultipartFile file) throws Exception {
        //获取第二个sheet名称
        HSSFSheet sheet=ExcelUtils.getSheet(file,1);
        String sheetName=sheet.getSheetName();
        //获取所教学生级数,切割sheet名称
        String stuYear=sheetName.substring(0,3);

        //读取评价值不为空的列
        HSSFRow row = sheet.getRow(0);
        Iterator cells = row.cellIterator();
        HSSFCell s=null;
        while(cells.hasNext()){
            HSSFCell cell = (HSSFCell) cells.next();
            String val=cell.getStringCellValue();
            if("评价值".equals(val)){
                s=cell;
                break;
            }
        }

        if(s!=null){
            int s_col=s.getColumnIndex();//评价值所在列的列号
            List<MapCourseIndex> courseIndices=new ArrayList<>();
            int count=0;
            String indexName=null;//当前指标点的名称
            Double indexValue=null;//当前指标点的值

            for(int i = sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
                HSSFRow s_row = sheet.getRow(i);
                HSSFCell s_first=s_row.getCell(0);
                String val= (String) ExcelUtils.getCellValue(s_first);
                if(!"".equals(val)){
                    String[] vals=val.split(" ");
                    indexName=vals[0];
                    for(int j=i+1;j<=sheet.getLastRowNum();j++){
                        HSSFRow now=sheet.getRow(j);
                        HSSFCell nowCell=now.getCell(s_col);
                        Double f= (Double) ExcelUtils.getCellValue(nowCell);
                        if(f!=null){
                            indexValue=f;
                            //将对应的值填写到类再添加到数据库中
                            MapCourseIndex newCourseIndex=new MapCourseIndex();
                            newCourseIndex.setCourseId(classId);
                            newCourseIndex.setStatisticYear(AcademicYear.getStartYear());
                            newCourseIndex.setProportionValue(indexValue);
                            newCourseIndex.setId(IdGen.uuid());
                            courseIndices.add(newCourseIndex);
                            i=j;
                            break;
                        }else{
                            String firstRowValue= (String) ExcelUtils.getCellValue(now.getCell(0));//当前行的评价值名称
                            if(firstRowValue!=null){
                                System.out.println(indexName+"项的值不能为空");
                                i=j;
                                break;
                            }
                        }
                    }
                }
            }
            mapCourseIndexMapper.insertList(courseIndices);
        }

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

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        mapStudentEvaluationMapper.insertList(mapStudentEvaluations);
    }
}