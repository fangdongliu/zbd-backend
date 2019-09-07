package cn.fdongl.point.util.excel;

import com.glf.jxframe.modules.lab.entity.functionlab.SysDict;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ExcelSpaceUseImport {

    public static <E> List<E> getListFromExcelWithpeople(List<E> lwp, MultipartFile file,List<SysDict> peopelType)throws Exception{
        List<String> columnName=new ArrayList<>();
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();

        Workbook workbook=null;
        if(type.equals("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        else if(type.equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        else{
            throw new Exception("无理取闹的文件类型");
        }

        //获取所有的列头元素
        Sheet sheet=workbook.getSheetAt(0);
        for(Cell cell:sheet.getRow(0)){
            columnName.add(cell.getStringCellValue());
        }



        return lwp;
    }
}
