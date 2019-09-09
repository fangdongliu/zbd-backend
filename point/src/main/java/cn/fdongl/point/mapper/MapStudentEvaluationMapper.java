package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapStudentEvaluation;

public interface MapStudentEvaluationMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapStudentEvaluation record);

    int insertSelective(MapStudentEvaluation record);

    MapStudentEvaluation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapStudentEvaluation record);

    int updateByPrimaryKey(MapStudentEvaluation record);
}