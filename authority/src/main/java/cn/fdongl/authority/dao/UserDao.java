package cn.fdongl.authority.dao;

import cn.fdongl.authority.vo.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    /**
     * 需要 实现
     * @param name 用户名
     * @return 查找用户的所有数据，二次查询
     */
    User findByUsername(String name);

}
