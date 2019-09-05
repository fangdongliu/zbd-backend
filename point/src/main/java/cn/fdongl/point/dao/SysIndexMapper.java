package cn.fdongl.point.dao;


import cn.fdongl.point.entity.SysIndex;

public interface SysIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysIndex record);

    int insertSelective(SysIndex record);

    SysIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysIndex record);

    int updateByPrimaryKey(SysIndex record);
}