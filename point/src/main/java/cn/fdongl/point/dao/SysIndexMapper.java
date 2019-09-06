package cn.fdongl.point.dao;

import cn.fdongl.point.entity.SysIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysIndex record);

    int insertList(List<SysIndex> list);

    int insertSelective(SysIndex record);

    SysIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysIndex record);

    int updateByPrimaryKey(SysIndex record);

    SysIndex selectByIdAndDate(String number);

}