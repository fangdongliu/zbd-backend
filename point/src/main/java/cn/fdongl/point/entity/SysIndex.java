package cn.fdongl.point.entity;

import cn.fdongl.authority.util.IdGen;
import lombok.Data;

import java.util.Date;

@Data
public class SysIndex {
    /**
    * 主键id
    */
    private String id;

    /**
    * 指标点要求序号 (4.1/4.2)
    */
    private String indexNumber;

    /**
    * 指标点大项标题
    */
    private String indexTitle;

    /**
    * 指标要求内容
    */
    private String indexContent;

    /**
    * 指标要求父类型 - from sys_index 表
    */
    private String parentId;

    /**
    * 指标要求序号
    */
    private Integer sort;

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

    public SysIndex() {
        this.id = IdGen.uuid();
    }

    public void setUUId(){
        this.id = IdGen.uuid();
    }
}