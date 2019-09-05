package cn.fdongl.sys.entity;

import java.util.Date;
import lombok.Data;

@Data
public class SysDict {
    /**
    * 主键id
    */
    private String id;

    /**
    * 字典项名称
    */
    private String dictName;

    /**
    * 字典类型 from 字典类型表sys_dict_type
    */
    private String typeId;

    /**
    * 字典项排序值
    */
    private Integer sort;

    /**
    * 父字典项 from 字典表
    */
    private String parentId;

    /**
    * 字典项备注
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