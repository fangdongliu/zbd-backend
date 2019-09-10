package cn.fdongl.point.entity;

import java.util.Date;

import cn.fdongl.authority.util.IdGen;
import lombok.Data;

@Data
public class MapDepartmentIndex {
    /**
     * 主键id
     */
    private String id;

    /**
     * 指标点主键id(from sys_index)
     */
    private String indexId;

    /**
     * 学生级数
     */
    private String studentGrade;

    /**
     * 学院(机构)名称
     */
    private String departmentName;

    /**
     * 专业名称
     */
    private String majorityName;

    /**
     * 期数
     */
    private Integer period;

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

    public MapDepartmentIndex() {
        this.id = IdGen.uuid();
    }

    public void setUUId(){
        this.id = IdGen.uuid();
    }
}