package cn.fdongl.point.service.impl;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.Result;
import cn.fdongl.point.entity.TwoString;
import cn.fdongl.point.service.ExportService;

import cn.fdongl.point.util.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

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
@Service
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
        int singleColumnWidth = 300;
        // 定义样式
        HSSFCellStyle style = wb.createCellStyle();
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        // 粗体
        font.setBold(true);
        // 宋体
        font.setFontName("宋体");
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        style1.setFont(font);
        // 水平居中
        style1.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        // 自动换行
        style1.setWrapText(true);

        // 遍历指标点大类
        for (Map.Entry<String, Result> entry : stringResultMap.entrySet()) {
            String bigIndexNumber = entry.getKey();
            Result mapValue = entry.getValue();
            // 新建大类 sheet
            HSSFSheet sheet = wb.createSheet("毕业要求(" + bigIndexNumber + ")");
            // 合并单元格（title）
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    mapValue.getIndexList().size()));
            // 设置列宽度(像素)
            for (int i = 0; i < mapValue.getIndexList().size() + 1; i++) {
                sheet.setColumnWidth(i, 32 * singleColumnWidth);
            }
            // 添加title
            HSSFRow row0 = sheet.createRow((int) 0);
            row0.setHeight((short) 0x289);
            HSSFCell cell = row0.createCell(0);
            cell.setCellValue(bigTitle);
            cell.setCellStyle(style1);
            // 添加表格头(7.1、7.2、7.3)
            HSSFRow row1 = sheet.createRow((int) 1);
            row1.setHeight((short) 0x489);
            for (int i = 1; i < mapValue.getIndexList().size() + 1; i++) {
                // 0列为空
                HSSFCell cell1 = row1.createCell(i);
                // 1列其实是对应的指标点的第0个
                cell1.setCellValue(mapValue.getIndexList().get(i - 1));
                cell1.setCellStyle(style1);
            }
            // 循环课程行逐个添加
            for (int i = 0; i < mapValue.getCourseList().size(); i++) {
                // 前两行是表头
                HSSFRow row2 = sheet.createRow(i + 2);
                row2.setHeight((short) 0x289);
                int cellNum = 0;
                for (int j = 0; j < mapValue.getIndexList().size() + 1; j++) {
                    HSSFCell cell2 = row2.createCell(cellNum++);
                    if (j == 0) {
                        // 0列应该是课程名称
                        cell2.setCellValue(mapValue.getCourseList().get(i));
                    }else{
                        // 1列之后是对应的空或者评价值
                        TwoString tmpTwoString = new TwoString(
                                mapValue.getCourseList().get(i),
                                mapValue.getIndexList().get(j - 1));
                        if (mapValue.getRelation().get(tmpTwoString) == null) {
                            cell2.setCellValue("");
                        } else {
                            cell2.setCellValue(mapValue.getRelation().get(tmpTwoString));
                        }
                    }
                    cell2.setCellStyle(style);
                }
            }
            // 最后一行是合计
            // 添加表格头(7.1、7.2、7.3)
            HSSFRow row3 = sheet.createRow((int) mapValue.getCourseList().size() + 2);
            row3.setHeight((short) 0x289);
            for (int i = 0; i < mapValue.getIndexList().size() + 1; i++) {
                HSSFCell cell3 = row3.createCell(i);
                if (i == 0) {
                    cell3.setCellValue("合计");
                } else {
                    cell3.setCellValue("");
                }
                cell3.setCellStyle(style1);
            }
        }
        httpServletResponse.setContentType("application/vnd.ms-excel");

        String oralName = "毕业要求达成程度" + DateUtils.nyrFormat(new Date());
        String fileName = new String( oralName.getBytes( "gb2312" ), "ISO8859-1" );
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        httpServletResponse.setContentType("application/x-download; charset=UTF-8");

        OutputStream ouputStream = httpServletResponse.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
}
