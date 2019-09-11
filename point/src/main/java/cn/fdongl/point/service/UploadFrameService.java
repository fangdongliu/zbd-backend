package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.MapStudentCourse;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 上传学院纲要
 */
public interface UploadFrameService {

    /**
     * 上传培养方案
     * @param projectFile
     */
    String uploadProject(MultipartFile projectFile, HttpServletRequest request, JwtUser user) throws IOException;

    /**
     * 上传课程培养标准实现矩阵
     * @param cultivateMatrix
     */
    void uploadCultivateMatrix(MultipartFile cultivateMatrix,JwtUser user) throws IOException;

    /**
     * 上传教师信息
     * @param teacherFile
     */
    void uploadTeacherInfo(MultipartFile teacherFile,JwtUser user) throws IOException;

    /**
     * 上传学生选课信息
     * @param studentCourse
     */
    void uploadStudentCourse(MultipartFile studentCourse,JwtUser user,String id) throws IOException;
}
