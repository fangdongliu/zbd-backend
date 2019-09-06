package cn.fdongl.authority.controller;

import cn.fdongl.authority.service.SysUserService;
import cn.fdongl.authority.tool.AjaxMessage;
import cn.fdongl.authority.tool.MsgType;
import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.authority.vo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName SysUserController
 * @Description 用户接口控制器
 * @Author zm
 * @Date 2019/9/6 12:12
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户接口")
public class SysUserController {
    private AjaxMessage retMsg = new AjaxMessage();

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "getInfo")
    @ResponseBody
    public Object getInfo(@RequestParam(value = "userId") String userId){
        SysUser theUser = userService.selectByPrimaryKey(userId);
        if (theUser != null){
            return retMsg.Set(MsgType.SUCCESS,theUser,"获取用户信息成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "更新用户信息失败");
        }
    }

    @ApiOperation(value = "更新用户信息")
    @RequestMapping(value = "updateInfo")
    @ResponseBody
    public Object updateInfo(@RequestBody SysUser updateUser){
        if(userService.updateByPrimaryKeySelective(updateUser) == 1){
            return retMsg.Set(MsgType.SUCCESS, userService.selectByPrimaryKey(updateUser.getId()),
                    "更新用户信息成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "更新用户信息失败");
        }
    }

    @ApiOperation(value = "管理员添加新用户")
    @RequestMapping(value = "addNew")
    @ResponseBody
    public Object addNew(
            JwtUser userNow,
            @RequestParam("userName") String userName,
            @RequestParam("userType") String userType,
            @RequestParam("userDepartment") String userDepartment
    ){
        Date tmpDate = new Date();
        SysUser newUser = new SysUser();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUserName(userName);
        newUser.setUserType(userType);
        newUser.setUserDepartment(userDepartment);
        newUser.setCreateDate(tmpDate);
        newUser.setModifyDate(tmpDate);
        newUser.setCreateUserId(userNow.getId());
        newUser.setModifyUserId(userNow.getId());
        if(userService.insertSelective(newUser) == 1){
            return retMsg.Set(MsgType.SUCCESS, userService.selectByPrimaryKey(newUser.getId()),
                    "新增用户成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "新增用户失败");
        }
    }
}
