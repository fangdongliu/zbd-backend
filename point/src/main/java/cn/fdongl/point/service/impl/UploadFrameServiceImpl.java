package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.mapper.MapCourseIndexMapper;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.entity.MapCourseIndex;
import cn.fdongl.point.entity.SysCourse;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.service.UploadFrameService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    public void uploadProject(MultipartFile projectFile) throws IOException {
        int sheetNum= ExcelUtils.getSheetNum(projectFile);
        List<SysCourse> courses=new ArrayList<>();
        if(sheetNum!=0){
            //循环遍历每个表中的sheet，将每个sheet中的课程信息录入
            for(int i=0;i<sheetNum;i++){
                Sheet nowSheet=ExcelUtils.getSheet(projectFile,i);
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

}
