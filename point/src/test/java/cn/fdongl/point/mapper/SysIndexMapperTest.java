package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.SysIndex;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysIndexMapperTest
 * @Description TODO
 * @Author zm
 * @Date 2019/9/11 16:47
 * @Version 1.0
 **/
public class SysIndexMapperTest {
    private static SysIndexMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(SysIndexMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/SysIndexMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(SysIndexMapper.class, builder.openSession(true));
    }

    @Test
    public void testSelectByIds() throws FileNotFoundException {
        List<String> ids = new ArrayList<>();
        ids.add("132aa636434d41e2afcbea1432f30295");
        ids.add("1647661a32d34d3c9fdf19dab0401f1a");
        List<SysIndex> sysIndexList = mapper.selectByIds(ids);
        System.out.println("ok");
        System.out.println(sysIndexList);
    }
}
