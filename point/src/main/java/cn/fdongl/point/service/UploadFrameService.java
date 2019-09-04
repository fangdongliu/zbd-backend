package cn.fdongl.point.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传学院纲要
 */
public interface UploadFrameService {

    /**
     * 上传培养方案
     * @param projectFile
     */
    void uploadProject(MultipartFile projectFile);

    /**
     * 上传课程对应指标点
     * @param classPointFile
     */
    void uploadClassPoint(MultipartFile classPointFile);

}
