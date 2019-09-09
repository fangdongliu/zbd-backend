package cn.fdongl.point.mapper;

import org.apache.ibatis.annotations.Param;


import cn.fdongl.point.entity.SysDict;

import java.util.List;

public interface SysDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    List<String> selectDictNameByTypeId(@Param("typeId")String typeId);
}