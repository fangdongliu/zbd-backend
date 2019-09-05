package cn.fdongl.authority.vo;

import lombok.Data;
import org.springframework.stereotype.Indexed;

import java.util.Date;
import java.util.List;

@Data
public class User {
    /**
     * need implement 需要定义自己的属性
     */
    private String id;
    private String userName;
    private String userPwd;
    private Date lastPasswordResetDate;

    private String realName;
    private String workId;
    private String userType;
    private String userDepartment;
    private String className;
    private String startYear;
    private int educationSystem;
    private String trainLevel;
    private String userTitle;

    private String createUserId;
    private Date createDate;
    private String modifyUserId;
    private Date modifyDate;
    private int status;

    private List<String> roles;
}
