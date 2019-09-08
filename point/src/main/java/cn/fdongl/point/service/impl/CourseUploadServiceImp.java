package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.IdGen;
import cn.fdongl.point.entity.SysCourse;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.mapper.SysCourseMapper;
import cn.fdongl.point.service.CourseUploadService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    public String uploadExecuteClass(MultipartFile file) throws IOException {
        // 获取Excel的输出流
        InputStream inputStream = file.getInputStream();
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // init工作簿
        Workbook workbook = null;
        // 获取文件后缀
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        // 根据不同后缀init不同的类，是xls还是xlsx
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = null;
            return "请上传正确的表格文件";
        }
        // 如果上传为非excel文件，返回
        if (workbook == null) {
            return "请上传文件";
        }

        // init
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        List list = new ArrayList<>();

        for (int t = 0; t < workbook.getNumberOfSheets(); t++) {
            sheet = workbook.getSheetAt(t);
            if (sheet == null) {
                continue;
            }
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }

        }

        // 关闭流
        workbook.close();
        inputStream.close();
        List<SysCourse> courses = new ArrayList<>();

        //获取第一行
        List<Object> first = (List<Object>) list.get(0);
        HSSFCell nameCell = (HSSFCell) first.get(0);
        String name = nameCell.getRichStringCellValue().getString();
        String schoolYear = name.substring(1, 12);//从标题中获取当前学年学期
        for (int i = 3; i < list.size(); i++) {
            List<Object> lo = (List<Object>) list.get(i);
            SysCourse sysCourse = new SysCourse();
            for (int j = 0; j < lo.size(); j++) {
                HSSFCell cell1 = (HSSFCell) lo.get(j);
                String val = cell1.getRichStringCellValue().getString();
                switch (j) {
                    //输入课程代码
                    case 0: {
                        sysCourse.setCourseNumber(val);
                    }
                    case 1: {
                        sysCourse.setCourseName(val);
                    }
                    case 2: {
                        Integer num = Integer.getInteger(val);
                        sysCourse.setTotalLen(num);
                    }
                    case 3: {
                        Integer num = Integer.getInteger(val);
                        sysCourse.setSemesterLen(num);
                    }
                    case 4: {
                        Double num = Double.valueOf(val);
                        sysCourse.setCourseCredit(num);
                    }
                    case 5: {
                        sysCourse.setCourseType(val);
                    }
                    case 6: {
                        sysCourse.setCourseAttribution(val);
                    }
                    case 7: {
                        sysCourse.setCourseDepartment(val);
                    }
                    case 8: {
                        sysCourse.setCourseRoute(val);
                    }
                }
            }
            courses.add(sysCourse);
        }
        sysCourseMapper.insertList(courses);

        return null;

    }

}
