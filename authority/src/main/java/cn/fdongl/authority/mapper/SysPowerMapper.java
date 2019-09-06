package cn.fdongl.authority.mapper;


import cn.fdongl.authority.vo.SysPower;

public interface SysPowerMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysPower record);

    int insertSelective(SysPower record);

    SysPower selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPower record);

    int updateByPrimaryKey(SysPower record);
}