package cn.fdongl.point.mapper;

import cn.fdongl.authority.util.SearchResult;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @ClassName MapStudentCourseMapperTest
 * @Description TODO
 * @Author zm
 * @Date 2019/9/10 19:40
 * @Version 1.0
 **/
public class MapStudentCourseMapperTest {
    private static MapStudentCourseMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(MapStudentCourseMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/MapStudentCourseMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(MapStudentCourseMapper.class, builder.openSession(true));
    }

    @Test
    public void testSelectCourseNameAndCourseSelectNumberPageByUserWorkIdAndCourseSemester() throws FileNotFoundException {
        List<SearchResult> searchResults = mapper.selectCourseInfoPageByUserWorkIdAndCourseSemester(
                "1120161982", "2018-2019-1", 0, 2);
        System.out.println(searchResults);
    }

    @Test
    public void testSelectSearchResultNumByUserWorkIdAndCourseSemester() throws FileNotFoundException {
        int ree = mapper.selectSearchResultNumByUserWorkIdAndCourseSemester("1120161982","2018-2019-1");
        System.out.println(ree);
    }
}