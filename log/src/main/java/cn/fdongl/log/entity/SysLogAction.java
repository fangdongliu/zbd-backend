package cn.fdongl.log.entity;

import lombok.Data;

@Data
public class SysLogAction {
    /**
    * 权限id
    */
    private String id;

    /**
    * URL
    */
    private String actionUrl;

    /**
    * 操作所属的模块
    */
    private String actionModule;

    /**
    * 动作描述
    */
    private String actionDesc;
}