package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapCourseIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapCourseIndexMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapCourseIndex record);

    int insertSelective(MapCourseIndex record);

    MapCourseIndex selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapCourseIndex record);

    int updateByPrimaryKey(MapCourseIndex record);

    /**
     * 根据课程编号获取最近的对应的指标点
     *
     * @author zm
     * @param courseNumber  课程编号
     * @return java.util.List<java.lang.String>
     * @date 2019/9/11 16:25
     **/
    List<String> selectRecentIndexIdByCourseNumber(String courseNumber);
}