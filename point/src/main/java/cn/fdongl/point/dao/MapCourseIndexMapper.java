package cn.fdongl.point.dao;

import cn.fdongl.point.entity.MapCourseIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapCourseIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapCourseIndex record);

    int insertSelective(MapCourseIndex record);

    int insertList(List<MapCourseIndex> records);

    MapCourseIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapCourseIndex record);

    int updateByPrimaryKey(MapCourseIndex record);
}