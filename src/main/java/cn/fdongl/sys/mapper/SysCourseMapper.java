package cn.fdongl.sys.mapper;

import cn.fdongl.sys.entity.SysCourse;

public interface SysCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysCourse record);

    int insertSelective(SysCourse record);

    SysCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysCourse record);

    int updateByPrimaryKey(SysCourse record);
}