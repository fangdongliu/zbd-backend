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
        try{
            classPointService.savePoint(classId,file);
            return retMsg.Set(MsgType.SUCCESS);
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //学生评教
    @PostMapping(value = "studentCom")
    public Object uploadStudentCom(@RequestParam("classId") String classId,
                                   @RequestParam("map") Map<String,Integer> map,
                                   @RequestParam("studentId") String studentId
                                   ){
        try{
            classPointService.savePoint(classId,map,studentId);
            return retMsg.Set(MsgType.SUCCESS);
        }catch(Exception e){
            return retMsg.Set(MsgType.ERROR);
        }

    }


    //上传培养方案
    @PostMapping(value = "cultivatePlan")
    public Object uploadCultivatePlan(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        try{
            System.out.println("上传培养方案");
            uploadFrameService.uploadProject(file,request);
            return retMsg.Set(MsgType.SUCCESS);
        }catch (Exception e){
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //上传培养矩阵
    @PostMapping(value = "/cultivateMatrix")
    public Object uploadCultivateMatrix(@RequestParam("file") MultipartFile file){
        String msg=null;
        try{
            msg=uploadFrameService.uploadClassPoint(file);
            if(msg==null){
                return retMsg.Set(MsgType.SUCCESS);
            }else{
                return retMsg.Set(MsgType.ERROR,null,msg);
            }
        }catch(Exception e){
            return retMsg.Set(MsgType.SUCCESS)  ;
        }
    }

    @ApiOperation(value = "上传教师信息")
    @PostMapping(value = "teacherInfo")
    public Object uploadTeacherInfo (
            @RequestParam("file") MultipartFile teacherFile
    ){
        System.out.println("上传教师信息");
        String res = "";
        if (teacherFile == null || teacherFile.isEmpty()){
            return retMsg.Set(MsgType.ERROR,null,"文件不能为空");
        }
        try{
            res = uploadFrameService.uploadTeacherInfo(teacherFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retMsg.Set(MsgType.SUCCESS,null,res);
    }

    @ApiOperation(value = "上传学生选课信息")
    @PostMapping(value = "studentCourse")
    public Object uploadStudentCourse (
            @RequestParam("file") MultipartFile studentCourseFile
    ){
        System.out.println("上传学生选课信息");
        String res = "";
        if (studentCourseFile == null || studentCourseFile.isEmpty()){
            return retMsg.Set(MsgType.ERROR,null,"文件不能为空");
        }
        try{
            res = uploadFrameService.uploadStudentCourse(studentCourseFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retMsg.Set(MsgType.SUCCESS,null,res);
    }

    @PostMapping(value = "courseUpload")
    public Object uploadCourse(@RequestParam("file") MultipartFile file) {
        String msg=null;
        try {
            msg=courseUploadService.uploadExecuteClass(file);
            if(msg!=null){
                return retMsg.Set(MsgType.ERROR,null,msg);
            }
        } catch (Exception e) {
            return retMsg.Set(MsgType.ERROR);
        }
        return retMsg.Set(MsgType.SUCCESS);
    }

    @PostMapping(value = "teacherCourseUpload")
    public Object teacherCourseUpload(@RequestParam("file") MultipartFile file){
        String msg=null;
        try{
            msg=courseUploadService.uploadTeacherCourse(file);
            if(msg!=null){
                return retMsg.Set(MsgType.ERROR,null,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return retMsg.Set(MsgType.ERROR);
        }
        return retMsg.Set(MsgType.SUCCESS);
    }

}