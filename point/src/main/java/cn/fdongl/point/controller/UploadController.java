package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.service.CourseUploadService;
import cn.fdongl.point.service.UploadFrameService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/upload")
@RestController
public class UploadController extends BaseController {

    @Autowired
    private ClassPointService classPointService;
    @Autowired
    private UploadFrameService uploadFrameService;
    @Autowired
    private CourseUploadService courseUploadService;


    //老师上传评价表
    @PostMapping(value = "/uploadTeacherCom")
    public Object uploadTeacherCom(@RequestParam("classId")String classId,
                                   @RequestParam("file")MultipartFile file){

        String msg=null;
        try{
            msg=classPointService.savePoint(classId,file);
            if(msg!=null){
                return retMsg.Set(MsgType.ERROR,null,msg);
            }

        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
        return retMsg.Set(MsgType.SUCCESS);
    }


    /**
     * 上传学生评价
     *
     * @param courseSelectNumber 选课课号(from map_teacher_course)
     * @param data map对象，key为指标点ID，value为分数（1-4）
     * @param studentWorkId 学生工号/学生用户名
     * @return java.lang.Object
     * @author zm
     * @date 2019/9/9 10:36
     **/
    @PostMapping(value = "studentEvaluation")
    public Object uploadStudentCom(
            @RequestParam("studentWorkId") String studentWorkId,
            @RequestParam("courseSelectNumber") String courseSelectNumber,
            @RequestParam("data") Map<String, Integer> data
    ) {
        try {
            classPointService.savePoint(courseSelectNumber, data, studentWorkId);
            return retMsg.Set(MsgType.SUCCESS);
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //上传培养方案
    @PostMapping(value = "cultivatePlan")
    public Object uploadCultivatePlan(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            System.out.println("上传培养方案");
            uploadFrameService.uploadProject(file, request);
            return retMsg.Set(MsgType.SUCCESS);
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
    }


    /**
     * 上传培养矩阵
     *
     * @author zm
     * @param file e.g. 软件学院2016版培养方案-3-培养标准实现矩阵2016级
     * @return java.lang.Object
     * @date 2019/9/9 19:41
     **/
    @PostMapping(value = "cultivateMatrix")
    public Object uploadCultivateMatrix(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("开始上传培养矩阵");
            uploadFrameService.uploadCultivateMatrix(file);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
        System.out.println("传培养矩阵上传完毕");
        return retMsg.Set(MsgType.SUCCESS);
    }

    /**
     * 上传教师信息
     *
     * @author zm
     * @param teacherFile
     * @return java.lang.Object
     * @date 2019/9/10 14:52
     **/
    @PostMapping(value = "teacherInfo")
    public Object uploadTeacherInfo(
            @RequestParam("file") MultipartFile teacherFile
    ) {
        System.out.println("上传教师信息");
        if (teacherFile == null || teacherFile.isEmpty()) {
            return retMsg.Set(MsgType.ERROR, null, "文件不能为空");
        }
        try {
            uploadFrameService.uploadTeacherInfo(teacherFile);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.SUCCESS, null, "上传教师信息失败");
        }
        System.out.println("上传教师信息成功");
        return retMsg.Set(MsgType.SUCCESS, null, "上传教师信息成功");
    }

    /**
     * 上传学生选课信息
     *
     * @author zm
     * @param studentCourseFile
     * @return java.lang.Object
     * @date 2019/9/10 14:52
     **/
    @PostMapping(value = "studentCourse")
    public Object uploadStudentCourse(
            @RequestParam("file") MultipartFile studentCourseFile
    ) {
        if (studentCourseFile == null || studentCourseFile.isEmpty()) {
            return retMsg.Set(MsgType.ERROR, null, "文件不能为空");
        }
        try {
            uploadFrameService.uploadStudentCourse(studentCourseFile);
        } catch (Exception e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.SUCCESS, null, "上传学生选课信息失败");
        }
        System.out.println("上传学生选课信息成功");
        return retMsg.Set(MsgType.SUCCESS, null, "上传学生选课信息成功");
    }

    @PostMapping(value = "courseUpload")
    public Object uploadCourse(@RequestParam("file") MultipartFile file) {
        String msg = null;
        try {
            msg = courseUploadService.uploadExecuteClass(file);
            if (msg != null) {
                return retMsg.Set(MsgType.ERROR, null, msg);
            }
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
        return retMsg.Set(MsgType.SUCCESS);
    }

    @PostMapping(value = "teacherCourseUpload")
    public Object teacherCourseUpload(@RequestParam("file") MultipartFile file) {
        String msg = null;
        try {
            msg = courseUploadService.uploadTeacherCourse(file);
            if (msg != null) {
                return retMsg.Set(MsgType.ERROR, null, msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
        return retMsg.Set(MsgType.SUCCESS);
    }
}