package cn.fdongl.point.mapper;


import cn.fdongl.point.entity.SysCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysCourse record);

    int insertBatch(List<SysCourse>list);

    int insertSelective(SysCourse record);

    SysCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysCourse record);

    int updateByPrimaryKey(SysCourse record);

    String selectIbByNumber(String number);

    List<String> getAllCourseNum();


}