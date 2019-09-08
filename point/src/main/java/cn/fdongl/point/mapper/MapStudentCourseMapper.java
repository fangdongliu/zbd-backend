package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapStudentCourse;

public interface MapStudentCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapStudentCourse record);

    int insertSelective(MapStudentCourse record);

    MapStudentCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapStudentCourse record);

    int updateByPrimaryKey(MapStudentCourse record);
}