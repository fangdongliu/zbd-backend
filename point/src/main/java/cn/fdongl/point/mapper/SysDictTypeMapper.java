package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.SysDictType;import org.apache.ibatis.annotations.Param;

public interface SysDictTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

    String selectIdByTypeName(@Param("typeName") String typeName);
}