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

    /**
     * 根据id集合查询返回SysIndex
     *
     * @author zm
     * @param indexIds
     * @return java.util.List<cn.fdongl.point.entity.SysIndex>        
     * @date 2019/9/11 16:53
     **/
    List<SysIndex> selectByIds(List<String> indexIds);
}