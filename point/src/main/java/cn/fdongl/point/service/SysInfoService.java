package cn.fdongl.point.service;

import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.util.SearchResult;
import cn.fdongl.point.entity.MapStudentEvaluation;
import cn.fdongl.point.entity.SysIndex;

import java.util.List;

/**
 * 获取系统信息的服务接口
 */
public interface SysInfoService {
    /**
     * 获取所有学院信息
     *
     * @param []
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/9 12:12
     **/
    List<String> getDepartment();

    /**
     * 获取学生的所有的学期
     *
     * @param studentWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:20
     **/
    List<String> getStudentSemesters(String studentWorkId);

    /**
     * 获取教师的所有学期
     *
     * @param teacherWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:20
     **/
    List<String> getTeacherSemesters(String teacherWorkId);


    /***
     * 获取学生课程分页
     *
     * @author zm
     * @param studentWorkId
     * @param courseSemester
     * @param pageIndex
     * @param pageSize
     * @return cn.fdongl.authority.util.Page<cn.fdongl.authority.util.SearchResult>
     * @date 2019/9/10 17:16
     **/
    Page<SearchResult> getStudentCoursePage(String studentWorkId, String courseSemester, int pageIndex, int pageSize);

    /**
     * 获取学生的历史评价(针对以往上的课)没有返回空
     *
     * @author zm
     * @param studentWorkId 学生工号
     * @param courseSelectNumber 选课课号
     * @return java.util.List<cn.fdongl.point.entity.MapStudentEvaluation>
     * @date 2019/9/10 20:11
     **/
    List<SysIndex> getStudentEvaluation(String studentWorkId, String courseSelectNumber);

    /**
     * 获取当前时间(学期)的指定课程对应的指标点List
     *
     * @author zm
     * @param courseNumber  选课课号
     * @return java.util.List<cn.fdongl.point.entity.SysIndex>
     * @date 2019/9/11 16:19
     **/
    List<SysIndex> getNowCourseIndex(String courseNumber);
}