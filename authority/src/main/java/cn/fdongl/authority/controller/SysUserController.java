package cn.fdongl.authority.controller;

import cn.fdongl.authority.service.SysUserService;
import cn.fdongl.authority.util.AjaxMessage;
import cn.fdongl.authority.util.MsgType;
import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.authority.vo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取用户信息")
    @PostMapping(value = "getInfo")
    public Object getInfo(@RequestParam(value = "userId") String userId){
        SysUser theUser = sysUserService.selectByPrimaryKey(userId);
        if (theUser != null){
            return retMsg.Set(MsgType.SUCCESS,theUser,"获取用户信息成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "更新用户信息失败");
        }
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "updateInfo")
    public Object updateInfo(
            JwtUser jwtUser,
            @RequestParam("userId") String userId,
            @RequestParam("newPassword") String newPassword,
            @RequestParam(value = "newRealName",required = false) String newRealName
    ){
        Date dateNow = new Date();

        SysUser theUser = sysUserService.selectByPrimaryKey(userId);

        theUser.setLastPasswordResetDate(dateNow);
        theUser.setModifyDate(dateNow);
        theUser.setModifyUserId(jwtUser.getId());
        if (newRealName != null){
            theUser.setRealName(newRealName);
        }
        // 修改为设置新的加密密码
        theUser.setSecretePwd(passwordEncoder.encode(newPassword));

        if(sysUserService.updateByPrimaryKeySelective(theUser) == 1){
            return retMsg.Set(MsgType.SUCCESS, theUser,
                    "更新用户信息成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "更新用户信息失败");
        }
    }

    @ApiOperation(value = "管理员添加新用户")
    @PostMapping(value = "addNew")
    public Object addNew(
            JwtUser userNow,
            @RequestParam("userName") String userName,
            @RequestParam("userType") String userType,
            @RequestParam("departmentName") String departmentName
    ){
        // 1.首先判定是不是存在用户名重复
        if (sysUserService.findUserByUserName(userName)!=null){
            return retMsg.Set(MsgType.ERROR,null, "用户名重复");
        }
        Date tmpDate = new Date();
        SysUser newUser = new SysUser();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUserName(userName);
        newUser.setUserType(userType);
        newUser.setUserDepartment(departmentName);
        // 设置默认密码为：123456(加密存储)
        newUser.setSecretePwd("123456");
        newUser.setCreateDate(tmpDate);
        newUser.setModifyDate(tmpDate);
        newUser.setCreateUserId(userNow.getId());
        newUser.setModifyUserId(userNow.getId());
        if(sysUserService.insertSelective(newUser) == 1){
            return retMsg.Set(MsgType.SUCCESS, sysUserService.selectByPrimaryKey(newUser.getId()),
                    "新增用户成功");
        }else{
            return retMsg.Set(MsgType.ERROR,null, "新增用户失败");
        }
    }

    @ApiOperation(value = "获取用户分页")
    @PostMapping(value = "getAll")
    public Object getAll(
            @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("searchKey") String searchKey
    ){
        SysUser userSearch = new SysUser();
        userSearch.setPage(new Page<SysUser>());

        userSearch.getPage().setPageIndex(pageIndex);
        userSearch.getPage().setPageSize(pageSize);
        userSearch.getPage().setSearchKey(searchKey);

        List<SysUser> userList = sysUserService.selectPageWithCondition(userSearch);
        int userTotal = sysUserService.selectNumWithCondition(userSearch);

        Page<SysUser> userPage = new Page<>();
        userPage.setResultList(userList);
        userPage.setTotal(userTotal);

        return retMsg.Set(MsgType.SUCCESS,userPage,"获取用户分页成功");
    }

    @ApiOperation(value = "批量删除用户（假删）")
    @PostMapping(value = "deleteBatch")
    public Object deleteBatch(@RequestBody List<SysUser> userList){
        System.out.println(userList);
        if (sysUserService.deleteByIds(userList) == userList.size()){
            return retMsg.Set(MsgType.SUCCESS,null,"删除用户成功");
        }
        return retMsg.Set(MsgType.ERROR,null,"删除用户失败");
    }

    /**
     * 根据用户类型获取用户的所有学期List
     *
     * @author zm
     * @param userId
     * @param userType
     * @return java.lang.Object        
     * @date 2019/9/9 18:50
     **/
    @PostMapping(value = "getSemester")
    public Object getUserSemesters(
            @RequestParam("userId") String  userId,
            @RequestParam("userType") String userType
    ){
        return retMsg.Set(MsgType.SUCCESS);
    }
}
