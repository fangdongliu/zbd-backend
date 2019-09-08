package cn.fdongl.point.entity;

import java.util.Date;

import cn.fdongl.authority.util.IdGen;
import lombok.Data;

@Data
public class MapStudentCourse {
    /**
     * 主键id
     */
    private String id;

    /**
     * 学生work_id from sys_user 表
     */
    private String userWorkId;

    /**
     * 总成绩
     */
    private String totalGrade;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     * 课程选课课号 - from map_teacher_course 表
     * (学期-课程编号-教师工号-该师该学期第几门课)
     */
    private String courseSelectNumber;

    /**
     * 开课学期(2017-2018-1)
     */
    private String courseSemester;

    /**
     * 课程性质
     */
    private String courseNature;

    /**
     * 课程属性
     */
    private String courseProperty;

    /**
     * 课程归属
     */
    private String courseAscription;

    /**
     * 课程种类
     */
    private String courseKind;

    /**
     * 课程学时
     */
    private Integer courseHour;

    /**
     * 课程学分
     */
    private Double courseCredit;

    /**
     * 开课单位
     */
    private String courseDepartment;

    /**
     * 成绩录入人名称
     */
    private String inputUserName;

    /**
     * 成绩标志
     */
    private String gradeSign;

    /**
     * 考试性质
     */
    private String examNature;

    /**
     * 补重学期
     */
    private String supplementRepeatSemester;

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

    public void setUUId() {
        this.id = IdGen.uuid();
    }
}