package cn.fdongl.point.service;

import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ClassPointService {

    /**
     * 老师评教
     * @param classId 课程ID
     * @param file 上传的excel文件
     */

    void savePoint(Integer classId, MultipartFile file) throws IOException;

    /**
     * 学生评教
     * @param classId 课程ID
     * @param data map对象，key为指标点ID，value为分数（1-4）
     */
    void savePoint(Integer classId, Map<Integer, Integer> data);

}
