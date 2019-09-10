package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.util.SearchResult;
import cn.fdongl.point.mapper.*;
import cn.fdongl.point.service.SysInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysInfoServiceImpl
 * @Description TODO
 * @Author zm
 * @Date 2019/9/9 12:13
 * @Version 1.0
 **/
@Service
public class SysInfoServiceImpl implements SysInfoService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;
    @Autowired
    private MapStudentCourseMapper mapStudentCourseMapper;
    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;
    @Autowired
    private SysCourseMapper sysCourseMapper;

    /**
     * 获取所有学院信息
     *
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/9 12:12
     **/
    @Override
    public List<String> getDepartment() {
        String departmentTypeId = sysDictTypeMapper.selectIdByTypeName("学院机构");
        return sysDictMapper.selectDictNameByTypeId(departmentTypeId);
    }

    /**
     * 获取学生的所有的学期
     *
     * @param studentWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:20
     **/
    @Override
    public List<String> getStudentSemesters(String studentWorkId) {
        return mapStudentCourseMapper.selectDistinctCourseSemesterByUserWorkIdDeOrderByCourseSemester(studentWorkId);
    }

    /**
     * 获取教师的所有学期
     *
     * @param teacherWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:20
     **/
    @Override
    public List<String> getTeacherSemesters(String teacherWorkId) {
        return mapTeacherCourseMapper.selectDistinctCourseSemesterByTeacherWorkIdDeOrderByCourseSemester(teacherWorkId);
    }

    /***
     * 获取学生课程分页
     *
     * @author zm
     * @param studentWorkId
     * @param courseSemester
     * @param pageIndex
     * @param pageSize
     * @return cn.fdongl.authority.util.Page<cn.fdongl.point.entity.SysCourse>
     * @date 2019/9/10 16:05
     **/
    @Override
    public Page<SearchResult> getStudentCoursePage(String studentWorkId, String courseSemester, int pageIndex, int pageSize) {
        // 根据学生工号和课程学期分页获得所有的课程的  课程名称和选课课号
        List<SearchResult> searchResultList =
                mapStudentCourseMapper.selectCourseNameAndCourseSelectNumberPageByUserWorkIdAndCourseSemester(
                        studentWorkId, courseSemester, (pageIndex - 1) * pageSize, pageSize);
        System.out.println("输出课程名称和选课课号");
        System.out.println(searchResultList);

        int searchResultTotal = mapStudentCourseMapper.selectSearchResultNumByUserWorkIdAndCourseSemester(
                        studentWorkId, courseSemester);

        System.out.println("该学期该课程所有数目");
        System.out.println(searchResultTotal);

        Page<SearchResult> resultPage = new Page<SearchResult>();
        resultPage.setResultList(searchResultList);
        resultPage.setTotal(searchResultTotal);

        System.out.println(resultPage);
        return resultPage;
    }
}