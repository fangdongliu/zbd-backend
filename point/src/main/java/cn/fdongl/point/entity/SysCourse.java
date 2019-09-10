package cn.fdongl.point.entity;

import cn.fdongl.authority.util.Page;
import lombok.Data;

import java.util.Date;

@Data
public class SysCourse {
    /**
    * 主键id
    */
    private String id;

    /**
    * 课程名称
    */
    private String courseName;

    /**
    * 课程学分
    */
    private Double courseCredit;

    /**
    * 课程代码/编码
    */
    private String courseNumber;

    /**
    * 开课学年学期(示例：2018-2019-1)
    */
    private String courseSemester;

    /**
    * 课程开课单位/院系/部门
    */
    private String courseDepartment;

    /**
    * 课程所在路线
    */
    private String courseRoute;

    /**
    * 课程性质/属性
    */
    private String courseCharacter;

    /**
    * 课程体系/类别
    */
    private String courseType;

    /**
    * 课程种类(文学与艺术)
    */
    private String courseKind;

    /**
    * 课程归属(文化素质通识课)
    */
    private String courseAttribution;

    /**
    * 课程考核方式
    */
    private String assessMethod;

    /**
    * 培养环节类别标识
    */
    private String typeIdentification;

    /**
    * 模块与层次标识
    */
    private String moduleIdentification;

    /**
    * 是否可用高层次课程代替代课程(-1不可，0可)
    */
    private Integer isSubstitute;

    /**
    * 课程总学时
    */
    private Integer totalLen;

    /**
    * 课程学期学时(一般和课程总学时相同)
    */
    private Integer semesterLen;

    /**
    * 课程上机/实验学时
    */
    private Integer experimentLen;

    /**
    * 备注
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

    /**
     * 课程分页
     */
    private transient Page<SysCourse> page;
}