package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapCourseEvaluation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MapCourseEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapCourseEvaluation record);

    int insertList(List<MapCourseEvaluation> list);

    int insertSelective(MapCourseEvaluation record);

    MapCourseEvaluation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapCourseEvaluation record);

    int updateByPrimaryKey(MapCourseEvaluation record);
}