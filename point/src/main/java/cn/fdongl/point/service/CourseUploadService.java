package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CourseUploadService {

    String uploadExecuteClass(MultipartFile file, JwtUser user) throws IOException;

    String uploadTeacherCourse(MultipartFile file ,JwtUser user) throws IOException;
}
