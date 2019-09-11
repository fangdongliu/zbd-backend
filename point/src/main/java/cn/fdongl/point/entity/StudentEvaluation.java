package cn.fdongl.point.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SysEvaluation
 * @Description 评价类
 * @Author zm
 * @Date 2019/9/10 20:41
 * @Version 1.0
 **/
@Data
public class StudentEvaluation {
    /**
     * 学生工号
     **/
    private String studentWorkId;

    /**
     * 课程编号
     **/
    private String courseNumber;

    /**
     * 选课课号
     **/
    private String courseSelectNumber;

    /**
     * 学生评价：
     * indexId 指标点主键id
     * evaluationValue 评价值(0-4)
     **/
    private List<MapStudentEvaluation> evaluations;
}
