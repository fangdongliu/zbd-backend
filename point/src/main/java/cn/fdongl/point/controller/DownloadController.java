package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.SysFile;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysFileMapper;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
    @GetMapping(value = "downOldTeacherEvaluation")
    public String oldTeacherEvaluation(@RequestParam("id") String id,
                                       HttpServletResponse response) {
        MapTeacherCourse mapTeacherCourse = mapTeacherCourseMapper.selectByPrimaryKey(id);
        String fileId = mapTeacherCourse.getFileId();
        SysFile file = sysFileMapper.selectByPrimaryKey(fileId);

        String fileName = file.getFileName();
        String filePath = file.getFilePath();
        //下载文件
        if (fileName != null) {
            File file1 = new File(filePath, fileName);
            if (file1.exists()) {
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                // response.setContentType("application/force-download");
                try {
                    response.setHeader("Content-Disposition", "attachment;fileName=" +   URLEncoder.encode(fileName,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null; //文件输入流
                BufferedInputStream bis = null;

                OutputStream os = null; //输出流
                try {
                    os = response.getOutputStream();
                    fis = new FileInputStream(file1);
                    bis = new BufferedInputStream(fis);
                    int i = bis.read(buffer);
                    while(i != -1){
                        os.write(buffer);
                        i = bis.read(buffer);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("----------file download---" + fileName);
                try {
                    bis.close();
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
