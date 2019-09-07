package cn.fdongl.point.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ExcelGenerator {
    public static void generateExcel(List<String> headp,List<List<String>>data,  HttpServletResponse resp, String filename, String sheetName) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet(sheetName);

        HSSFCellStyle borderCell = workbook.createCellStyle();
        borderCell.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        borderCell.setBorderTop(HSSFCellStyle.BORDER_THIN);
        borderCell.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        borderCell.setBorderRight(HSSFCellStyle.BORDER_THIN);

        try {
            HSSFRow head = sheet.createRow(0);

            int columnCount = headp.size();

            HSSFCellStyle headStyle = workbook.createCellStyle();

            headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

            headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            int[] lens = new int[columnCount];

            for (int i = 0; i < columnCount; i++) {
                lens[i] = 0;
            }

            for (int i = 0; i < columnCount; i++) {
                HSSFCell headCell = head.createCell(i);
                headCell.setCellStyle(headStyle);
                headCell.setCellValue(headp.get(i));
                int nextLen = headp.get(i).length();
                if (lens[i] < nextLen)
                    lens[i] = nextLen;
            }

            for(int i=1;i<=data.size();i++){
                List<String>line = data.get(i-1);
                HSSFRow rowData = sheet.createRow(i);
                for (int j = 0; j < columnCount; j++) {
                    HSSFCell cell = rowData.createCell(j);
                    cell.setCellStyle(borderCell);
                    if(line.get(j)!=null) {
                        cell.setCellValue(line.get(j));
                        int nextLen = line.get(j).length();
                        if (lens[j] < nextLen)
                            lens[j] = nextLen;
                    }
                }
            }

            for (int i = 0; i < columnCount; i++) {
                sheet.setColumnWidth(i, lens[i] * 2 * 256+2);
            }

            resp.setHeader("Content-disposition", "attachment; filename="+filename);
            resp.setContentType("application/msexcel");
            workbook.write(resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
