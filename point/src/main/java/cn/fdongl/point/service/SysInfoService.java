package cn.fdongl.point.service;

import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.util.SearchResult;

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
}