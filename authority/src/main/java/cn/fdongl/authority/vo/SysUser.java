package cn.fdongl.authority.vo;

import cn.fdongl.authority.util.Page;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@Data
public class SysUser {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 学号/工号
     */
    private String workId;

    /**
     * 用户类型/账号类型
     */
    private String userType;

    /**
     * 所属部门/学院
     */
    private String userDepartment;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 入学年份
     */
    private String startYear;

    /**
     * 学生学制
     */
    private Integer educationSystem;

    /**
     * 培养层次
     */
    private String trainLevel;

    /**
     * 教师职称
     */
    private String userTitle;

    /**
     * 最后一次修改密码时间
     */
    private Date lastPasswordResetDate;

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
     * 该用户对应的权限
     **/
    private List<String> roles;

    /**
     * 用户分页
     **/
    private Page<SysUser> page;

    public void setSecretePwd(String userPwd){
        this.userPwd = new BCryptPasswordEncoder().encode(userPwd);
    }
}