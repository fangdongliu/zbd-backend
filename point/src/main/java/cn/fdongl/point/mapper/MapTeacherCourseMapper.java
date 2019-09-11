package cn.fdongl.point.mapper;

import org.apache.ibatis.annotations.Param;

import cn.fdongl.point.entity.MapTeacherCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapTeacherCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapTeacherCourse record);

    int insertSelective(MapTeacherCourse record);

    MapTeacherCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapTeacherCourse record);

    int updateByPrimaryKey(MapTeacherCourse record);

    List<MapTeacherCourse> getMapTeacherCourseByPage(MapTeacherCourse map);

    int getTotal(MapTeacherCourse mapTeacherCourse);

    List<MapTeacherCourse> getOldMapTeacherCourseByPage(MapTeacherCourse map);

    int getOldTotal(MapTeacherCourse mapTeacherCourse);

    /**
     * 逆序获得教师学期
     *
     * @param teacherWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:26
     **/
    List<String> selectDistinctCourseSemesterByTeacherWorkIdDeOrderByCourseSemester(
            @Param("teacherWorkId") String teacherWorkId);

    /**
     * 根据选课课号获取id
     *
     * @param courseSelectNumbers 选课课号
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 16:51
     **/
    List<String> selectIdByCourseSelectNumbers(
            @Param("courseSelectNumbers") List<String> courseSelectNumbers);

    List<String> selectIdBySemesters(List<String> semesters);


}