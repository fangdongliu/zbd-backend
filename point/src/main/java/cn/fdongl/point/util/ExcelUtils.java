package cn.fdongl.point.util;


import net.sf.json.JSONArray;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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
    public static Sheet getSheet(MultipartFile excel, int sheetIndex) throws IOException {
        XSSFWorkbook wb = null;
        wb = new XSSFWorkbook (excel.getInputStream());
        //获取sheet
        Sheet sheet = wb.getSheetAt(sheetIndex);

        System.out.println("sheet name = "+wb.getSheetName(0));

        return sheet;
    }

    //获取表格总的sheet数
    public static int  getSheetNum(MultipartFile excel) throws IOException {
        XSSFWorkbook wb = null;
        wb = new XSSFWorkbook(excel.getInputStream());
        return wb.getNumberOfSheets();
    }

    // 通过table中的字段类型获取对应excel类型值
    public static Object getCellValueByFieldType(Cell cell, String fieldType) {
        Object val = null;
        try {
            switch (fieldType) {
                case "varchar":
                    cell.setCellType(Cell.CELL_TYPE_STRING);
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
    public static Object getCellValue(Cell cell) {
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

    //返回指定的行中哪一列等于特定的值
    public static int getSpeCol(Sheet sheet,String content,int rowNum){
        Row row = sheet.getRow(rowNum);
        Iterator cells = row.cellIterator();
        Cell s=null;
        int coLNum=-1;
        while(cells.hasNext()){
            Cell cell = (Cell) cells.next();
            String val=cell.getStringCellValue();
            if(content.equals(val)){
                s=cell;
                coLNum=s.getColumnIndex();
                break;
            }
        }
        return coLNum;
    }

    /**
     * 判断指定单元格是不是合并单元格
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public  static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue1(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 获取单元各的值
     * @param cell
     * @return
     */
    public static String getCellValue1(Cell cell){
        if(cell == null) return "";
        return cell.getStringCellValue();
    }

    /**
     * 根据Cell获取对应的String类型值
     *
     * @author zm
     * @param
     * @return java.lang.Object
     * @date 2019/9/9 21:17
     **/
    public static Object getJavaValue(XSSFCell cell) {
        Object o = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case XSSFCell.CELL_TYPE_BLANK:
                o = "";
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_ERROR:
                o = "Bad value!";
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                o = getValueOfNumericCell(cell);
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                try {
                    o = getValueOfNumericCell(cell);
                } catch (IllegalStateException e) {
                    try {
                        o = cell.getRichStringCellValue().toString();
                    } catch (IllegalStateException e2) {
                        o = cell.getErrorCellString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                o = cell.getRichStringCellValue().getString();
        }
        return o;
    }

    // 获取数字类型的cell值
    private static Object getValueOfNumericCell(XSSFCell cell) {
        Boolean isDate = DateUtil.isCellDateFormatted(cell);
        Double d = cell.getNumericCellValue();
        Object o = null;
        if (isDate) {
            o = DateFormat.getDateTimeInstance()
                    .format(cell.getDateCellValue());
        } else {
            o = getRealStringValueOfDouble(d);
        }
        return o;
    }

    // 处理科学计数法与普通计数法的字符串显示，尽最大努力保持精度
    private static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            // 小数部分
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint
                    + BigInteger.ONE.intValue(), indexOfE));
            // 指数
            int pow = Integer.valueOf(doubleStr.substring(indexOfE
                    + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            java.util.regex.Pattern p = Pattern.compile(".0$");
            java.util.regex.Matcher m = p.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            }
        }
        return doubleStr;
    }

    //获取文件输出流
    public static InputStream getResourcesFileInputStream(String fileName){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    public static String inputStreamToString(InputStream in) throws IOException {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        int len=0;
        byte[] b=new byte[1024];
        while((len=in.read(b,0,b.length))!=-1){
            baos.write(b,0,len);
        }
        byte[] buffer=baos.toByteArray();
        return Base64.encodeBase64String(buffer);
    }
}



