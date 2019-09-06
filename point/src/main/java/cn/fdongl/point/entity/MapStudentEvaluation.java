package cn.fdongl.point.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MapStudentEvaluation {
    /**
    * 主键id
    */
    private String id;

    /**
    * 学生/教师工号
    */
    private String workId;

    private String indexId;

    /**
    * 选课课号(学期-课程编号-教师工号-该师该学期第几门课)
    */
    private String courseSelectNumber;

    /**
    * 指标要求number
    */
    private String indexNumber;

    /**
    * 课程指标点评价值(0->4)
    */
    private Integer commentValue;

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