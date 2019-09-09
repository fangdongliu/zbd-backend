package cn.fdongl.point.entity;

import java.util.Date;
import lombok.Data;

@Data
public class MapStudentEvaluation {
    /**
    * 主键id
    */
    private String id;

    /**
    * map_student_course表中的主键id
    */
    private String mapStudentCourseId;

    /**
    * 指标点主键id
    */
    private String indexId;

    /**
    * 课程指标点评价值(0->4 逐级递增)
    */
    private Integer evaluationValue;

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
    * 修改时间
    */
    private Date modifyDate;

    /**
    * 状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)
    */
    private Integer status;
}