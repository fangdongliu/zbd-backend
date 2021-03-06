package cn.fdongl.authority.service;

import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.util.JwtUserFactory;
import cn.fdongl.authority.vo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JwtUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.findUserByUserName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            System.out.println("类：JwtUserDetailServiceImpl，方法：loadUserByUsername，查询到的用户："+sysUser);
            return JwtUserFactory.create(sysUser);
        }
    }
}
