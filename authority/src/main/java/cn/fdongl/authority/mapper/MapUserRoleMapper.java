package cn.fdongl.authority.mapper;

import cn.fdongl.authority.vo.MapUserRole;

public interface MapUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapUserRole record);

    int insertSelective(MapUserRole record);

    MapUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapUserRole record);

    int updateByPrimaryKey(MapUserRole record);
}