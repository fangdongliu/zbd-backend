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
    private String username;
    private String password;
//    private String email;
    private Date lastPasswordResetDate;
    private List<String> roles;
}
