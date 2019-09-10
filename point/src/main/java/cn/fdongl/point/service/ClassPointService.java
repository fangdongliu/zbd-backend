package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ClassPointService {

    /**
     * 老师评教
     * @param course_select_number 选课课号(from map_teacher_course)
     * @param file 上传的excel文件
     */

    String savePoint(String classId, MultipartFile file, JwtUser user) throws  Exception;

    /**
     * 学生上传新的课程评价
     *
     * @param studentWorkId         学生工号
     * @param courseSelectNumber    选课课号
     * @param studentEvaluationList 评价list -> 用于批量插入
     *                              courseSelectNumber：选课课号
     *                              indexId：指标点id
     *                              evaluationValue：评价值(0-4)
     */
    void savePoint(String studentWorkId, String courseSelectNumber, List<MapStudentEvaluation> studentEvaluationList);
}
