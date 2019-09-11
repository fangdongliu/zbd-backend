package cn.fdongl.point.entity;

import cn.fdongl.authority.util.IdGen;
import lombok.Data;

import java.util.Date;

@Data
public class MapCourseIndex {
    /**
     * 主键id
     */
    private String id;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     * 指标要求id
     */
    private String indexId;

    /**
     * 还不知道id时，暂存number
     */
    private String indexNumber;

    /**
     * 达成目标值，指标系数之和=1
     */
    private Double proportionValue;

    /**
     * 学生年级(e.g. 2016级)
     */
    private String schoolGrade;

    /**
     * 统计年份(示例：2014代表2014-2015学年)
     */
    private String statisticYear;

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
     * 状态值(-1失效，0默认值代表是导入时的初始设置值，1标识是某课程的计算结果值)
     */
    private Integer status;

    public MapCourseIndex() {
        this.id = IdGen.uuid();
    }

    public void setUUId(){
        this.id = IdGen.uuid();
    }
}