package cn.fdongl.authority.dao;

import cn.fdongl.authority.vo.SysRole;
import cn.fdongl.authority.vo.SysUser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

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
}
