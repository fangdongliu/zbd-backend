package cn.fdongl.point.service;

import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ClassPointService {

    /**
     * 老师评教
     * @param course_select_number 选课课号(from map_teacher_course)
     * @param file 上传的excel文件
     */

    String savePoint(String classId, MultipartFile file) throws  Exception;

    /**
     * 学生评教
     * @param courseSelectNumber 选课课号(from map_teacher_course)
     * @param data map对象，key为指标点ID，value为分数（1-4）
     * @param studentWorkId 学生工号/学生用户名
     */
    void savePoint(String courseSelectNumber, Map<String, Integer> data,String studentWorkId);
}
