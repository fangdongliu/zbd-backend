package cn.fdongl.point.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MapTeacherCourse {
    /**
    * 主键id
    */
    private String id;

    /**
    * 教师work_id - from sys_user 表
    */
    private String teacherWorkId;

    /**
    * 教师姓名
    */
    private String teacherRealName;

    /**
    * 课程id - from sys_course 表
    */
    private String courseId;

    /**
    * 课程编号
    */
    private String courseNumber;

    /**
    * 选课课号(学期-课程编号-教师工号-该师该学期第几门课)
    */
    private String courseSelectNumber;

    /**
    * 课程学期
    */
    private String courseSemester;

    /**
    * 课程名称
    */
    private String courseName;

    /**
    * 课程学分
    */
    private Double courseCredit;

    /**
    * 开课校区
    */
    private String courseCampus;

    /**
    * 功能区(上课地点)
    */
    private String courseArea;

    /**
    * 课程属性
    */
    private String courseProperty;

    /**
    * 课程考试方式
    */
    private String courseTestMethod;

    /**
    * 讲课班级名称
    */
    private String courseClass;

    /**
    * 选课人数
    */
    private Integer courseElectNumber;

    /**
    * 排课人数
    */
    private Integer courseArrangeNumber;

    /**
    * 讲课周次
    */
    private String courseTeacheWeek;

    /**
    * 课程周学时
    */
    private Integer weekLen;

    /**
    * 安排学时
    */
    private Integer planLen;

    /**
    * 课程讲课学时
    */
    private Integer teachLen;

    /**
    * 课程实践学时
    */
    private Integer practiceLen;

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
    * 状态值(-1失效，0默认值)
    */
    private Integer status;
}