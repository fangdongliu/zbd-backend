package cn.fdongl.point.controller;


import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.authority.util.Page;

import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;


import cn.fdongl.point.service.SysInfoService;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/getInfo")
public class InfoController extends BaseController {

    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;

    @Autowired
    private SysInfoService sysInfoService;

    /**
     * 获取教师评价
     *
     * @author hl
     * @param pageIndex
     * @param pageSize
     * @return java.lang.Object
     * @date 2019/9/9 12:06
     **/
    @PostMapping(value = "teacherEvaluation")
    public Object getAll(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize
    ){
        MapTeacherCourse mapTeacherCourse=new MapTeacherCourse();
        mapTeacherCourse.setPage(new Page<MapTeacherCourse>());

        mapTeacherCourse.getPage().setPageIndex(pageIndex);
        mapTeacherCourse.getPage().setPageSize(pageSize);

        List<MapTeacherCourse> mapTeacherCourses=mapTeacherCourseMapper.getMapTeacherCourseByPage(mapTeacherCourse);

       int total=mapTeacherCourseMapper.getTotal(mapTeacherCourse);


        Page<MapTeacherCourse> teacherCoursePage = new Page<>();
        teacherCoursePage.setResultList(mapTeacherCourses);
        teacherCoursePage.setTotal(total);

        return retMsg.Set(MsgType.SUCCESS,teacherCoursePage,"获取教师课程分页成功");
    }


    /**
     * 获取所有学院的列表
     *
     * @author zm
     * @param []
     * @return java.lang.Object
     * @date 2019/9/9 12:10
     **/
    @PostMapping(value = "departmentInfo")
    public Object getDepartmentInfo(){
        try {
            return retMsg.Set(MsgType.SUCCESS,sysInfoService.getDepartment(),"获取学院机构成功");
        }catch (Exception e){
            e.printStackTrace();
            return retMsg.Set(MsgType.SUCCESS,null,"获取学校机构失败");
        }
    }
}
