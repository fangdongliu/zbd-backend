package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.SysFile;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysFileMapper;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/download")
public class DownloadController extends BaseController {
    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;
    @Autowired
    private SysFileMapper sysFileMapper;


    /**
     * 下载往期教师评价表
     * @param id
     * @return
     */
    @GetMapping(value = "downOldTeacherEvaluation",produces = MediaType.ALL_VALUE)
    public void oldTeacherEvaluation(@RequestParam("id") String id,
                                       HttpServletResponse response) throws UnsupportedEncodingException {
        MapTeacherCourse mapTeacherCourse = mapTeacherCourseMapper.selectByPrimaryKey(id);
        String fileId = mapTeacherCourse.getFileId();
        SysFile file = sysFileMapper.selectByPrimaryKey(fileId);

        String fileName = file.getFileName();
        String filePath = file.getFilePath();
        //下载文件
        if (fileName != null) {
            File file1 = new File(filePath, fileName);
            if (file1.exists()) {
                response.setContentType("application/msexcel");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
                try {
                    InputStream in = new FileInputStream(file1);
                    ServletOutputStream out = response.getOutputStream();
                    final byte[] b = new byte[8192];
                    for (int r; (r = in.read(b)) != -1;) {
                        out.write(b, 0, r);
                    }
                    in.close();
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载与该用户所有有关的表
     */
    @GetMapping(value = "downloadUserTable",produces = MediaType.ALL_VALUE)
    public void userTable(@RequestParam("id") String id,
                                     HttpServletResponse response) throws UnsupportedEncodingException {
        SysFile file = sysFileMapper.selectByPrimaryKey(id);

        String fileName = file.getFileName();
        String filePath = file.getFilePath();
        //下载文件
        if (fileName != null) {
            File file1 = new File(filePath, fileName);
            if (file1.exists()) {
                response.setContentType("application/msexcel");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
                try {
                    InputStream in = new FileInputStream(file1);
                    ServletOutputStream out = response.getOutputStream();
                    final byte[] b = new byte[8192];
                    for (int r; (r = in.read(b)) != -1;) {
                        out.write(b, 0, r);
                    }
                    in.close();
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
