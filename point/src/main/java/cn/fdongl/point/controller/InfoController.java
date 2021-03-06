package cn.fdongl.point.controller;


import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.authority.util.Page;

import cn.fdongl.authority.util.SearchResult;
import cn.fdongl.point.entity.*;
import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.*;
import cn.fdongl.point.mapper.MapCourseEvaluationMapper;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;


import cn.fdongl.point.mapper.SysFileMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.service.ResultService;
import cn.fdongl.point.service.SysInfoService;
import cn.fdongl.point.util.AcademicYear;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/getInfo")
public class InfoController extends BaseController {
    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;
    @Autowired
    private SysInfoService sysInfoService;
    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private MapCourseEvaluationMapper mapCourseEvaluationMapper;
    @Autowired
    private SysIndexMapper sysIndexMapper;

    @Autowired
    private ResultService resultService;
    /**
     * 获取教师评价
     *
     * @param pageIndex
     * @param pageSize
     * @return java.lang.Object
     * @author hl
     * @date 2019/9/9 12:06
     **/
    @PostMapping(value = "teacherEvaluation")
    public Object getAll(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize,
            JwtUser user
    ) {
        MapTeacherCourse mapTeacherCourse = new MapTeacherCourse();
        mapTeacherCourse.setPage(new Page<MapTeacherCourse>());

        mapTeacherCourse.setTeacherWorkId(user.getUsername());
        mapTeacherCourse.getPage().setPageIndex(pageIndex);
        mapTeacherCourse.getPage().setPageSize(pageSize);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String t = null;
        if(month >= 8){
            t = year + "-" + (year + 1) + "-" + 1;
        } else if(month < 2){
            t = (year - 1) + "-" + year + "-" + 1;
        } else{
            t = (year - 1) + "-" + year + "-" + 2;
        }
        mapTeacherCourse.setCourseSemester(t);
        List<MapTeacherCourse> mapTeacherCourses = mapTeacherCourseMapper.getMapTeacherCourseByPage(mapTeacherCourse);

        System.out.println(year);
        System.out.println(month);

        int total = mapTeacherCourseMapper.getTotal(mapTeacherCourse);


        Page<MapTeacherCourse> teacherCoursePage = new Page<>();
        teacherCoursePage.setResultList(mapTeacherCourses);
        teacherCoursePage.setTotal(total);


        return retMsg.Set(MsgType.SUCCESS, teacherCoursePage, "获取教师课程分页成功");
    }

    /**
     * 获取教师往期评价
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping(value = "oldTeacherEvaluation")
    public Object getAllOld(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize,
            JwtUser user
    ) {
        MapTeacherCourse mapTeacherCourse = new MapTeacherCourse();
        mapTeacherCourse.setPage(new Page<MapTeacherCourse>());
        String startYear = AcademicYear.getStartYear();//获取学年
        String endYear = String.valueOf(Integer.parseInt(startYear) + 1);
        String sc = null;
        String nowYear = AcademicYear.getNowYear();
        if (nowYear.equals(startYear)) {
            //当前年份为开始年份
            sc = startYear + "-" + endYear + "-1";
        } else {
            sc = String.valueOf(Integer.parseInt(startYear) - 1) + "-" + startYear + "-2";
        }
        mapTeacherCourse.setCourseSemester(sc);
        mapTeacherCourse.setTeacherWorkId(user.getUsername());
        mapTeacherCourse.getPage().setPageIndex(pageIndex);
        mapTeacherCourse.getPage().setPageSize(pageSize);

        List<MapTeacherCourse> mapTeacherCourses = mapTeacherCourseMapper.getOldMapTeacherCourseByPage(mapTeacherCourse);

        int total = mapTeacherCourseMapper.getOldTotal(mapTeacherCourse);


        Page<MapTeacherCourse> teacherCoursePage = new Page<>();
        teacherCoursePage.setResultList(mapTeacherCourses);
        teacherCoursePage.setTotal(total);


        return retMsg.Set(MsgType.SUCCESS, teacherCoursePage, "获取教师课程分页成功");

    }

    /**
     * 获取用户所有的文件下载
     */
    @PostMapping(value = "getAllUserTables")
    public Object getAllUserTable(@RequestParam("pageIndex") int pageIndex,
                                  @RequestParam("pageSize") int pageSize,
                                  @RequestParam("keyWord") String keyWord,
                                  JwtUser user) {
        SysFile sysFile = new SysFile();
        sysFile.setPage(new Page<SysFile>());
        sysFile.setCreateUserId(user.getId());
        sysFile.getPage().setPageIndex(pageIndex);
        sysFile.getPage().setPageSize(pageSize);
        sysFile.setFileName(keyWord);

        List<SysFile> sysFiles = sysFileMapper.getSysFileByPage(sysFile);
        int total = sysFileMapper.getTotal(sysFile);

        Page<SysFile> filePage = new Page<>();
        filePage.setResultList(sysFiles);
        filePage.setTotal(total);


        return retMsg.Set(MsgType.SUCCESS, filePage, "获取教师课程分页成功");


    }


    /**
     * 获取所有学院的列表
     *
     * @param
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/9 12:10
     **/
    @PostMapping(value = "departmentInfo")
    public Object getDepartmentInfo() {
        try {
            return retMsg.Set(MsgType.SUCCESS, sysInfoService.getDepartment(), "获取学院机构成功");
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.SUCCESS, null, "获取学校机构失败");
        }
    }

    /**
     * 根据用户类型获取用户的所有学期List
     *
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/9 18:50
     **/
    @GetMapping(value = "semester")
    public Object getUserSemesters(
            JwtUser jwtUser
    ) {
        List<String> semesterList = new ArrayList<>();
        try {
            if ("student".equals(jwtUser.getUserType())) {
                // 学生就去 学生-课程关联表去找所有的学期
                semesterList = sysInfoService.getStudentSemesters(jwtUser.getWorkId());
            } else if ("teacher".equals(jwtUser.getUserType())) {
                // 老师就去 教师-课程关联表去找所有的学期
                semesterList = sysInfoService.getTeacherSemesters(jwtUser.getWorkId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR, null, "获取学期失败");
        }
        return retMsg.Set(MsgType.SUCCESS, semesterList, "获取学期成功");
    }

    /**
     * 获取本学期的所有课程分页
     *
     * @param studentWorkId
     * @param pageIndex
     * @param pageSize
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/11 15:32
     **/
    @PostMapping(value = "nowTermPage")
    public Object getNowTermCoursePage(
            @RequestParam("studentWorkId") String studentWorkId,
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize) {
        Page<SearchResult> coursePage = new Page<>();
        try {
            String courseSemester = AcademicYear.getNowSemester();
            coursePage = sysInfoService.getStudentCoursePage(
                    studentWorkId, courseSemester, pageIndex, pageSize);
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR, "获取本学期课程失败失败");
        }
        return retMsg.Set(MsgType.SUCCESS, coursePage, "获取本学期课程成功");
    }

    /**
     * 获取学生某学期所有的课程分页
     *
     * @param courseSemester 课程学期
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/10 15:45
     **/
    @PostMapping(value = "coursePage")
    public Object getCoursePage(
            JwtUser jwtUser,
            @RequestParam(value = "courseSemester",required = false) String courseSemester,
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize) {
        Page<SearchResult> coursePage = new Page<>();
        String workId = jwtUser.getWorkId();
        try {
            if(jwtUser.getUserType().equals("student")) {
                coursePage = sysInfoService.getStudentCoursePage(
                        workId, courseSemester, pageIndex, pageSize);
            }else{
                coursePage = sysInfoService.getTeacherCoursePage(
                        workId, courseSemester);
            }
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR, "获取学生课程失败失败");
        }
        return retMsg.Set(MsgType.SUCCESS, coursePage, "获取学生课程成功");
    }


    /**
     * 获取当前时间(学期)课程对应的指标点并返回
     *
     * @param courseNumber  课程编号
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/11 16:14
     **/
        @PostMapping(value = "nowCourseIndex")
    public Object getNowCourseIndex(
        @RequestParam("courseNumber") String courseNumber) {
        List<SysIndex> indexList = new ArrayList<>();
        try {
            indexList = sysInfoService.getNowCourseIndex(courseNumber);
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR, "获取当前课程指标点失败");
        }
        return retMsg.Set(MsgType.SUCCESS, indexList, "获取当前课程指标点成功");
    }

    /**
     * 获取学生的某课程的指标点评价
     *
     * @param studentWorkId      学生工号
     * @param courseSelectNumber 选课课号
     * @return 未评价返回 null，已评价返回指标点编号，指标点说明，指标点评价值
     * @author zm
     * @date 2019/9/10 15:38
     **/
    @PostMapping(value = "courseIndexEvaluation")
    public Object getCourseIndexEvaluation(
            @RequestParam("studentWorkId") String studentWorkId,
            @RequestParam("courseSelectNumber") String courseSelectNumber) {

        System.out.println("ok");
        List<SysIndex> indexList = new ArrayList<>();
        try {
            indexList = sysInfoService.getStudentEvaluation(studentWorkId, courseSelectNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR, null, "获取用户指标点评价失败");
        }
        if(indexList == null){
            return retMsg.Set(MsgType.SUCCESS, null, "未评价");
        }
        return retMsg.Set(MsgType.SUCCESS, indexList, "获取用户指标点评价成功");
    }


    /**
     * 按照学年（两年）获取课程指标点
     * @param sy
     * @return
     */
    @PostMapping(value="getIndexYearResult")
    public Object getIndexYearResult(
            @RequestParam("schoolYear") String sy
    ){
        Map<String,Result> resultMap=resultService.getIndexGradeResult(sy);

        return retMsg.Set(MsgType.SUCCESS, resultMap, "获取成功");
    }

    /**
     * 按照年级获取课程指标点
     *
     * @author hl
     * @param grade 年级
     * @return java.lang.Object
     * @date 2019/9/12 0:16
     **/
    @PostMapping(value="getIndexGradeResult")
    public Object getIndexGradeResult(
            @RequestParam("grade") String grade
    ){
        //获取课程评价值
        Map<String,Result> resultMap=resultService.getIndexGradeResult(grade);
        return retMsg.Set(MsgType.SUCCESS, resultMap, "获取成功");
    }
}
