package cn.fdongl.authority.mapper;

import cn.fdongl.authority.vo.MapUserRole;

import java.util.List;

public interface MapUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapUserRole record);

    int insertSelective(MapUserRole record);

    int insertBatch(List<MapUserRole>list);

    MapUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapUserRole record);

    int updateByPrimaryKey(MapUserRole record);
}