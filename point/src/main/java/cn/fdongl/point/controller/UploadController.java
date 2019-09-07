package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.service.UploadFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/upload")
@RestController
public class UploadController extends BaseController {

    @Autowired
    private ClassPointService classPointService;
    @Autowired
    private UploadFrameService uploadFrameService ;


    //老师上传评价表
    @RequestMapping(value = "/uploadTeacherCom",method = RequestMethod.POST)
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
    @RequestMapping(value = "studentCom",method = RequestMethod.POST)
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
    @RequestMapping(value = "cultivatePlan",method=RequestMethod.POST)
    @ResponseBody
    public Object uploadCultivatePlan(@RequestParam("file")MultipartFile file){
        try{
            System.out.println("上传培养方案");
            uploadFrameService.uploadProject(file);
            return retMsg.Set(MsgType.SUCCESS);
        }catch (Exception e){
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //上传培养矩阵
    @RequestMapping(value = "/cultivateMatrix",method=RequestMethod.POST)
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
