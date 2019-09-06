package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapStudentEvaluation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapStudentEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapStudentEvaluation record);

    int insertList(List<MapStudentEvaluation> list);

    int insertSelective(MapStudentEvaluation record);

    MapStudentEvaluation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapStudentEvaluation record);

    int updateByPrimaryKey(MapStudentEvaluation record);
}