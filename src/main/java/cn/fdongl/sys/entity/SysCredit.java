package cn.fdongl.sys.entity;

import java.util.Date;
import lombok.Data;

@Data
public class SysCredit {
    /**
    * 主键id
    */
    private String id;

    /**
    * 某一项得分名称
    */
    private String creditName;

    /**
    * 对应课程的id - from sys_course 表
    */
    private String courseId;

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