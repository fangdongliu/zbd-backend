package cn.fdongl.authority.vo;

import java.util.Date;

import cn.fdongl.authority.util.IdGen;
import lombok.Data;

@Data
public class MapUserRole {
    /**
    * 主键id
    */
    private String id;

    /**
    * 用户id from sys_user 表
    */
    private String userId;

    /**
    * 角色id，from sys_role 表
    */
    private String roleId;

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

    public void setUUId() {
        this.id = IdGen.uuid();
    }
}