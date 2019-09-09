package cn.fdongl.point.controller;


import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.vo.SysUser;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController extends BaseController {

    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;

    @RequestMapping(value = "getAllTeacherCom", method = RequestMethod.POST)
    @ResponseBody
    public Object getAll(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("searchKey") String searchKey
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
}
