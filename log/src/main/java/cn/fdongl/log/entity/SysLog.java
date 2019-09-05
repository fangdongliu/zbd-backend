package cn.fdongl.log.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {
    /**
    * 日志编号
    */
    private String id;

    /**
    * 日志模块
    */
    private String logModule;

    /**
    * 请求IP
    */
    private String requestIp;

    /**
    * 用户id from sys_user
    */
    private String userId;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 用户名称
    */
    private String userName;

    /**
    * 操作行为
    */
    private String logAction;

    /**
    * 请求结果
    */
    private String logResult;

    /**
    * 请求时间
    */
    private Date logTime;
}