package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.point.service.ClassPointService;
import cn.fdongl.point.service.UploadFrameService;
import io.swagger.annotations.ApiOperation;
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
    @RequestMapping("/cultivatePlan")
    @ResponseBody
    public Object uploadCultivatePlan(@RequestParam("file")MultipartFile file){
        try{
            uploadFrameService.uploadProject(file);
            return retMsg.Set(MsgType.SUCCESS);
        }catch (Exception e){
            return retMsg.Set(MsgType.ERROR);
        }
    }


    //上传培养矩阵
    @RequestMapping("/cultivateMatrix")
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
    @RequestMapping(value = "teacherInfo",method = RequestMethod.POST)
    @ResponseBody
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
}
