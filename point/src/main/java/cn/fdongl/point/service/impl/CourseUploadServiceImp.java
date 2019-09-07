package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.SysCourse;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.service.CourseUploadService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CourseUploadServiceImp implements CourseUploadService {

    @Autowired
    private SysCourseMapper sysCourseMapper;

    //上传本学期计划执行列表
    @Override
    public void uploadExecuteClass(MultipartFile file) throws IOException {

        int sheetNum= ExcelUtils.getSheetNum(file);
        List<SysCourse> courses=new ArrayList<>();

        if(sheetNum!=0){
            for(int i=0;i<sheetNum;i++){
                Sheet nowSheet=ExcelUtils.getSheet(file,i);
                int firstCol=ExcelUtils.getSpeCol(nowSheet,"课程代码",1);
                if(firstCol!=-1){
                    //在第二行中存在课程代码列
                    int secondCol=ExcelUtils.getSpeCol(nowSheet,"课程名称",1);
                    if(secondCol!=-1){
                        //在第二行中存在课程名称项
                        for(int j= 2;j<=nowSheet.getLastRowNum();j++){
                            //从第二行开始遍历表中所有课程
                            Row row = nowSheet.getRow(j);
                            Cell courseCodeCell=row.getCell(firstCol);
                            Cell courseNameCell=row.getCell(secondCol);
                            SysCourse newCourse=new SysCourse();
                            newCourse.setCourseNumber((String) ExcelUtils.getCellValue(courseCodeCell));
                            newCourse.setCourseName((String) ExcelUtils.getCellValue(courseNameCell));
                            newCourse.setId(IdGen.uuid());
                            Date now =new Date();
                            newCourse.setCreateDate(now);
                            courses.add(newCourse);
                        }
                    }
                }
            }
        }

        sysCourseMapper.insertList(courses);
    }
}
