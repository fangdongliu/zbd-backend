package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.SysIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    SysIndex selectByIdAndDate(@Param("number") String number);

    SysIndex selectByNumAndDate();

    List<SysIndex> selectByStudentWorkIdAndCourseSelectNumber(
            @Param("studentWorkId") String studentWorkId,
            @Param("courseSelectNumber") String courseSelectNumber);
}