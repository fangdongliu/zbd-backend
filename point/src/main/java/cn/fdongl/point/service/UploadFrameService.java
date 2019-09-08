package cn.fdongl.point.service;

import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 上传学院纲要
 */
public interface UploadFrameService {

    /**
     * 上传培养方案
     * @param projectFile
     */
    String uploadProject(MultipartFile projectFile, HttpServletRequest request) throws IOException;

    /**
     * 上传课程对应指标点
     * @param classPointFile
     */
    String uploadClassPoint(MultipartFile classPointFile) throws IOException;

    /**
     * 上传教师信息
     * @param teacherFile
     */
    String uploadTeacherInfo(MultipartFile teacherFile) throws IOException;
}
