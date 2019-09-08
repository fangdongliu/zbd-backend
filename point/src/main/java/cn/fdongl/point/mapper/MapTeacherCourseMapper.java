package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapTeacherCourse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapTeacherCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapTeacherCourse record);

    int insertSelective(MapTeacherCourse record);

    MapTeacherCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapTeacherCourse record);

    int updateByPrimaryKey(MapTeacherCourse record);
}