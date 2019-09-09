package cn.fdongl.authority.mapper;

import cn.fdongl.authority.util.Page;
import cn.fdongl.authority.vo.SysUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserMapperTest
 * @Description TODO
 * @Author zm
 * @Date 2019/9/6 11:11
 * @Version 1.0
 **/
public class SysUserMapperTest {
    private static SysUserMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(SysUserMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/SysUserMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(SysUserMapper.class, builder.openSession(true));
    }

    @Test
    public void testFindUserByUserName() throws FileNotFoundException {
        SysUser sysUser = mapper.findUserByUserName("1120161969");
        System.out.println(sysUser);
    }

    @Test
    public void testDeleteByIds() {
        SysUser userSearch = new SysUser();
        userSearch.setPage(new Page<SysUser>());

        userSearch.getPage().setPageIndex(1);
        userSearch.getPage().setPageSize(2);
        userSearch.getPage().setSearchKey("81");

        List<SysUser> resUsers = mapper.selectPageWithCondition(userSearch);
        int res = mapper.deleteByIds(resUsers);

        System.out.println(res);
    }

    @Test
    public void testSelectAllUserMap() throws FileNotFoundException {
        List<String> userList = mapper.selectAllUser();
        System.out.println(userList);
    }

    @Test
    public void pwdTest() {
        String pwd = new BCryptPasswordEncoder().encode("123456");
        System.out.println(pwd);
    }
}
