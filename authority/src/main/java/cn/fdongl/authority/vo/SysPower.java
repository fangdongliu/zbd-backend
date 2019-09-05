package cn.fdongl.authority.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SysPower {
    /**
    * 主键
    */
    private String id;

    /**
    * 权限名称
    */
    private String powerName;

    /**
    * 授权代码
    */
    private String powerCode;

    /**
    * 父权限id from sys_power 表
    */
    private String parentId;

    /**
    * 创建人id
    */
    private String createUserId;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 修改人id
    */
    private String modifyUserId;

    /**
    * 修改时间
    */
    private Date modifyDate;

    /**
    * 状态值(-1失效，0默认值)
    */
    private Integer status;
}