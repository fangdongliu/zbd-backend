package cn.fdongl.authority.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public class JwtUser implements UserDetails {
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

    private final Collection<? extends GrantedAuthority> authorities;

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 这个是自定义的，返回上次密码重置日期
    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
