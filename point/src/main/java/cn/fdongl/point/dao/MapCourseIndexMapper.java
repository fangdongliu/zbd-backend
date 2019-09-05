package cn.fdongl.point.dao;

import cn.fdongl.point.entity.MapCourseIndex;

public interface MapCourseIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapCourseIndex record);

    int insertSelective(MapCourseIndex record);

    MapCourseIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapCourseIndex record);

    int updateByPrimaryKey(MapCourseIndex record);
}