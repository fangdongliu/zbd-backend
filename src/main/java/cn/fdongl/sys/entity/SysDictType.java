package cn.fdongl.sys.entity;

import java.util.Date;
import lombok.Data;

@Data
public class SysDictType {
    /**
    * 主键id
    */
    private String id;

    /**
    * 字典类型名
    */
    private String typeName;

    /**
    * 字典类型排序值
    */
    private Integer sort;

    /**
    * 父字典类型 from 字典类型表
    */
    private String parentId;

    /**
    * 字典类型说明
    */
    private String remarks;

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