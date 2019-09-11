package cn.fdongl.point.service.impl;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.Result;
import cn.fdongl.point.entity.TwoString;
import cn.fdongl.point.service.ExportService;

import cn.fdongl.point.util.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName ExportServiceImpl
 * @Description TODO
 * @Author zm
 * @Date 2019/9/11 20:31
 * @Version 1.0
 **/
public class ExportServiceImpl implements ExportService {

    /**
     * 导出2学年的信息
     *
     * @param user 当前用户信息
     */
    @Override
    public void exportExcelResult(
            JwtUser user,
            Map<String, Result> stringResultMap,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 定义sheet 标题名
        String bigTitle = "课程毕业要求达成度评价表";
        // 定义列宽
        int singleColumnWidth = 35;
        // 定义样式
        HSSFCellStyle style = wb.createCellStyle();
        // 水平居中
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        //style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        // 粗体
        //font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        style1.setFont(font);
        // 水平居中
        //style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        //style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 遍历指标点大类
        for (Map.Entry<String, Result> entry : stringResultMap.entrySet()) {
            String bigIndexNumber = entry.getKey();
            Result mapValue = entry.getValue();
            // 新建大类 sheet
            HSSFSheet sheet = wb.createSheet("毕业要求(" + bigIndexNumber + ")");
            // 合并单元格（title）
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    mapValue.getIndexList().size() + 1));
            // 设置列宽度(像素)
            for (int i = 0; i < mapValue.getIndexList().size() + 1; i++) {
                sheet.setColumnWidth(i, 32 * singleColumnWidth);
            }
            // 添加title
            HSSFRow row = sheet.createRow((int) 0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(bigTitle);
            cell.setCellStyle(style1);
            // 添加表格头(7.1、7.2、7.3)
            row = sheet.createRow((int) 1);
            for (int i = 1; i < mapValue.getIndexList().size() + 1; i++) {
                // 0列为空
                HSSFCell cell1 = row.createCell(i);
                cell1.setCellValue(mapValue.getIndexList().get(i));
                cell1.setCellStyle(style1);
            }
            // 循环课程行逐个添加
            for (int i = 0; i < mapValue.getCourseList().size(); i++) {
                // 前两行是表头
                row = sheet.createRow(i + 2);
                int cellNum = 0;
                for (int j = 0; j < mapValue.getIndexList().size() + 1; j++) {
                    HSSFCell cell2 = row.createCell(cellNum++);
                    if (j == 0) {
                        TwoString tmpTwoString = new TwoString(
                                mapValue.getCourseList().get(i),
                                mapValue.getIndexList().get(j));
                        if (mapValue.getRelation().get(tmpTwoString) == null) {
                            cell2.setCellValue("");
                        } else {
                            cell2.setCellValue(mapValue.getRelation().get(tmpTwoString));
                        }
                        cell2.setCellStyle(style1);
                    }
                }
            }
            // 最后一行是合计
            // 添加表格头(7.1、7.2、7.3)
            row = sheet.createRow((int) mapValue.getCourseList().size() + 2);
            for (int i = 0; i < mapValue.getIndexList().size() + 1; i++) {
                HSSFCell cell3 = row.createCell(i);
                if (i == 0) {
                    cell3.setCellValue("合计");
                } else {
                    cell3.setCellValue("");
                }
                cell3.setCellStyle(style1);
            }
        }
        httpServletResponse.setContentType("application/vnd.ms-excel");

        httpServletResponse.setHeader("Content-disposition",
                "attachment;filename=" + new String("毕业要求达成度".getBytes("gb2312"), "ISO8859-1")
                        + DateUtils.nyrFormat(new Date()) + ".xls");
        OutputStream ouputStream = httpServletResponse.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
}
