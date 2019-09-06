package cn.fdongl.authority.util;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.authority.vo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(SysUser sysUser) {
        return new JwtUser(
                sysUser.getId(),
                sysUser.getUserName(),
                sysUser.getUserPwd(),
                sysUser.getLastPasswordResetDate(),
                sysUser.getRealName(),
                sysUser.getWorkId(),
                sysUser.getUserType(),
                sysUser.getUserDepartment(),
                sysUser.getClassName(),
                sysUser.getStartYear(),
                sysUser.getEducationSystem(),
                sysUser.getTrainLevel(),
                sysUser.getUserTitle(),
                sysUser.getCreateUserId(),
                sysUser.getCreateDate(),
                sysUser.getModifyUserId(),
                sysUser.getModifyDate(),
                sysUser.getStatus(),
                mapToGrantedAuthorities(sysUser.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
