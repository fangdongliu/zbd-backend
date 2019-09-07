package cn.fdongl.authority.controller;

import cn.fdongl.authority.util.JwtTokenUtil;
import cn.fdongl.authority.vo.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 获取用户认证信息
     *
     * @author zm
     * @param1 request
     * @return cn.fdongl.authority.vo.JwtUser        
     * @date 2019/9/7 13:30
     **/
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
        return jwtUser;
    }
}