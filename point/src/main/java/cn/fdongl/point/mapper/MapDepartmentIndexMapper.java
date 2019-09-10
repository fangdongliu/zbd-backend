package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapDepartmentIndex;

public interface MapDepartmentIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapDepartmentIndex record);

    int insertSelective(MapDepartmentIndex record);

    MapDepartmentIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapDepartmentIndex record);

    int updateByPrimaryKey(MapDepartmentIndex record);
}