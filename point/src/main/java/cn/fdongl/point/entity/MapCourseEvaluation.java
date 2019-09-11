package cn.fdongl.point.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MapCourseEvaluation {
    /**
    * 主键id
    */
    private String id;

    private String indexId;

    /**
    * 学生年级
    */
    private String studentGrade;

    /**
    * 学年
    */
    private String schoolYear;

    /**
    * 指标点number(e.g.4.2, 4.3)
    */
    private String indexNumber;

    /**
    * 达成目标值(指标点系数)
    */
    private Double indexProportionValue;

    /**
    * 评价值(某课程针对其所需达成目标的实际评价值)
    */
    private Double evaluationValue;

    /**
    * 创建人id
    */
    private String createUserId;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 修改人id
    */
    private String modifyUserId;

    /**
    * 修改日期
    */
    private Date modifyDate;

    /**
    * 状态值(-1失效，0默认值)
    */
    private Integer status;

    private String courseId;

    private SysIndex sysIndex;

    private MapTeacherCourse mapTeacherCourse;
}