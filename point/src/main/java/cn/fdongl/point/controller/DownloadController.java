package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.SysFile;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
    @PostMapping(value = "oldTeacherEvaluation")
    public Object oldTeacherEvaluation(@RequestParam("id") String id,
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
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file1);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {

                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
