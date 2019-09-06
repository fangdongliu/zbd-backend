package cn.fdongl.point.controller;

import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.service.UploadFrameService;
import cn.fdongl.point.tool.BaseController;
import cn.fdongl.point.tool.MsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class UploadController extends BaseController {

    @Autowired
    private ClassPointService classPointService;
    @Autowired
    private UploadFrameService uploadFrameService ;


    //老师上传评价表
    @RequestMapping("")
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
    @RequestMapping("")
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
    @RequestMapping("")
    public Object uploadCultivatePlan( @RequestParam("file")MultipartFile file){
        try{
            uploadFrameService.uploadProject(file);
            return retMsg.Set(MsgType.SUCCESS);
        }catch (Exception e){
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //上传培养矩阵
    @RequestMapping("")
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
            return retMsg.Set(MsgType.SUCCESS);
        }
    }

}
