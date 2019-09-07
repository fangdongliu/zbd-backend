package cn.fdongl.point.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CourseUploadService {

    void uploadExecuteClass(MultipartFile file) throws IOException;
}
