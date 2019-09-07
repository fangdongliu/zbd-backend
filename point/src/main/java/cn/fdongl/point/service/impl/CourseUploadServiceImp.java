package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.SysCourse;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.service.CourseUploadService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
            for(int i=1;i<=sheetNum;i++){
                Sheet nowSheet=ExcelUtils.getSheet(file,i);
                Row f_row=nowSheet.getRow(1);//获取第一行
                Cell f_cell=f_row.getCell(1);
                String v= (String) ExcelUtils.getCellValue(f_cell);//获取标题的值
                String schoolYear=v.substring(1,12);//从标题中获取当前学年学期
                for(int j=4;j<=nowSheet.getLastRowNum();j++){
                    Row nowRow=nowSheet.getRow(j);//获取行
                    SysCourse sysCourse=new SysCourse();
                    sysCourse.setId(IdGen.uuid());
                    Iterator cells = nowRow.cellIterator();//遍历该行的单元格
                    sysCourse.setCourseSemester(schoolYear);
                    int count=1;
                    //创建指标点
                    while(cells.hasNext()){
                        Cell cell = (Cell) cells.next();
                        String val=cell.getStringCellValue();
                        if(val!=null){
                            switch (count){
                                //输入课程代码
                                case 1:{
                                    sysCourse.setCourseNumber(val);
                                }
                                case 2:{
                                    sysCourse.setCourseName(val);
                                }
                                case 3:{
                                    Integer num=Integer.getInteger(val);
                                    sysCourse.setTotalLen(num);
                                }
                                case 4:{
                                    Integer num=Integer.getInteger(val);
                                    sysCourse.setSemesterLen(num);
                                }
                                case 5:{
                                    Double num=Double.valueOf(val);
                                    sysCourse.setCourseCredit(num);
                                }
                                case 6:{
                                    sysCourse.setCourseType(val);
                                }
                                case 7:{
                                    sysCourse.setCourseAttribution(val);
                                }
                                case 8:{
                                    sysCourse.setCourseDepartment(val);
                                }
                                case 9:{
                                    sysCourse.setCourseRoute(val);
                                }
                            }
                        }
                    }
                    courses.add(sysCourse);
                }
            }
        }
        sysCourseMapper.insertList(courses);
    }

}
