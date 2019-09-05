package cn.fdongl.point.util;


import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class ExcelUtils {

    public final String XLSX = ".xlsx";
    public final String XLS=".xls";

    public int checkFile(MultipartFile file){
        if (file==null) {
            return 0;
        }
        String flieName = file.getName();
        if (flieName.endsWith(XLSX)) {
            return 1;
        }
        if (flieName.endsWith(XLS)) {
            return 2;
        }
        return 3;
    }


    // 获取excel文件中指定sheet
    public static HSSFSheet getSheet(MultipartFile excel, int sheetIndex) throws IOException {
        HSSFWorkbook wb = null;
        wb = new HSSFWorkbook(excel.getInputStream());

        //获取sheet
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        System.out.println("sheet name = "+wb.getSheetName(0));

        return sheet;
    }

    // 通过table中的字段类型获取对应excel类型值
    public static Object getCellValueByFieldType(HSSFCell cell, String fieldType) {
        Object val = null;
        try {
            switch (fieldType) {
                case "varchar":
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    val = cell.getStringCellValue();
                    break;
                case "int":
                    val = (int) cell.getNumericCellValue();
                    break;
                case "decimal":
                    // TODO
                    val = cell.getStringCellValue();
                    if (!StringUtils.isDigit((String) val)) {
                        val = "0";
                    }
                    break;
                case "float":
                    val = (float) cell.getNumericCellValue();
                    break;
                case "double":
                    val = cell.getNumericCellValue();
                    break;
                case "datetime":
                case "timestamp":
                case "date":
                    val = cell.getDateCellValue();
                    break;
            }
        } catch (Exception e) {
            // do nothing
        }
        return val;
    }

    // 获取excel行中的指定列中的值
    public static Object getCellValue(HSSFCell cell) {
        Object val;
        try {
            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        val = cell.getNumericCellValue();
                        break;
                    case Cell.CELL_TYPE_STRING:
                        val = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        val = cell.getCellFormula();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        val = cell.getBooleanCellValue();
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        val = cell.getErrorCellValue();
                        break;
                    default:
                        val = null;
                        break;
                }
            } else val = null;
        } catch (Exception e) {
            return null;
        }
        return val;
    }




}
