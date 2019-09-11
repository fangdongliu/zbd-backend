package cn.fdongl.point.service.impl;

import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.util.SearchResult;
import cn.fdongl.point.entity.MapStudentEvaluation;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.mapper.*;
import cn.fdongl.point.service.SysInfoService;
import cn.fdongl.point.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    private SysIndexMapper sysIndexMapper;
    @Autowired
    private MapCourseIndexMapper mapCourseIndexMapper;

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

    @Override
    public Page<SearchResult> getTeacherCoursePage(String workId, String courseSemester){
        if(courseSemester == null) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            if(month >= 8){
                courseSemester = year + "-" + (year+1);
            } else {
                courseSemester = (year - 1)+ "-" + year;
            }
        }
        List<SearchResult> searchResultList =
                mapTeacherCourseMapper.selectCourseInfoPageByUserWorkIdAndCourseSemester(workId, courseSemester);
        System.out.println("输出课程名称和选课课号");
        System.out.println(searchResultList);

        Page<SearchResult> resultPage = new Page<SearchResult>();
        resultPage.setResultList(searchResultList);

        System.out.println(resultPage);
        return resultPage;
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
        if(courseSemester == null) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            if(month >= 8){
                courseSemester = year + "-" + (year+1);
            } else {
                courseSemester = (year - 1)+ "-" + year;
            }
        }
        List<SearchResult> searchResultList =
                mapStudentCourseMapper.selectCourseInfoPageByUserWorkIdAndCourseSemester(
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

    /**
     * 获取学生的历史评价(针对以往上的课) for solve
     * step1：根据选课课号和学生工号在 map_student_course 中确定主键 id
     * step2：根据主键id在 map_student_evaluation 中插入新的数据
     *
     * @param studentWorkId      学生工号
     * @param courseSelectNumber 选课课号
     * @return java.util.List<cn.fdongl.point.entity.MapStudentEvaluation>
     * @author zm
     * @date 2019/9/10 20:11
     **/
    @Override
    public List<SysIndex> getStudentEvaluation(String studentWorkId, String courseSelectNumber) {
        return  sysIndexMapper.selectByStudentWorkIdAndCourseSelectNumber(studentWorkId, courseSelectNumber);
    }

    /**
     * 获取当前时间(学期)的指定课程对应的指标点List
     *
     * @param courseNumber  选课课号
     * @return java.util.List<cn.fdongl.point.entity.SysIndex>
     * @author zm
     * @date 2019/9/11 16:19
     **/
    @Override
    public List<SysIndex> getNowCourseIndex(String courseNumber) {
        //step1：首先在 map_course_index 中获取 courseNumber 对应的最新的 index_id
        List<String> indexIds = mapCourseIndexMapper.selectRecentIndexIdByCourseNumber(courseNumber);
        //step2:sysIndexMapper查询指标点list
        return sysIndexMapper.selectByIds(indexIds);
    }
}