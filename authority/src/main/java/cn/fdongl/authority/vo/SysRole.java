package cn.fdongl.authority.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    /**
     * 主键
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色授权码
     */
    private String roleCode;

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