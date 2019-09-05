package cn.fdongl.point.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MapCourseIndex {
    /**
    * 主键id
    */
    private String id;

    /**
    * 课程id，from sys_course 表
    */
    private String courseId;

    /**
    * 指标要求id ，from sys_index 表
    */
    private String indexId;

    /**
    * 达成目标值，指标系数之和=1
    */
    private Double proportionValue;

    /**
    * 统计年份(示例：2014-2015学年)
    */
    private String statisticYear;

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