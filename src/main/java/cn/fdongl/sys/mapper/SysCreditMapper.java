package cn.fdongl.sys.mapper;

import cn.fdongl.sys.entity.SysCredit;

public interface SysCreditMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysCredit record);

    int insertSelective(SysCredit record);

    SysCredit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysCredit record);

    int updateByPrimaryKey(SysCredit record);
}