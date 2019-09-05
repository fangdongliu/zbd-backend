package cn.fdongl.authority.util;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.authority.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getUserPwd(),
                user.getLastPasswordResetDate(),
                user.getRealName(),
                user.getWorkId(),
                user.getUserType(),
                user.getUserDepartment(),
                user.getClassName(),
                user.getStartYear(),
                user.getEducationSystem(),
                user.getTrainLevel(),
                user.getUserTitle(),
                user.getCreateUserId(),
                user.getCreateDate(),
                user.getModifyUserId(),
                user.getModifyDate(),
                user.getStatus(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
