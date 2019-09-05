package cn.fdongl.log.mapper;

import cn.fdongl.log.entity.SysLogAction;

public interface SysLogActionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLogAction record);

    int insertSelective(SysLogAction record);

    SysLogAction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLogAction record);

    int updateByPrimaryKey(SysLogAction record);
}