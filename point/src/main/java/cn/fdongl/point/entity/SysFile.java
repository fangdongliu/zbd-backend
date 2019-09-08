package cn.fdongl.point.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysFile {
    /**
    * 主键id
    */
    private String id;

    /**
    * 文件存放路径
    */
    private String filePath;

    /**
    * 文件名称
    */
    private String fileName;

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