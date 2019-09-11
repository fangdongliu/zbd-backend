package cn.fdongl.point.service;

import cn.fdongl.point.entity.MapCourseEvaluation;
import cn.fdongl.point.entity.Result;
import cn.fdongl.point.mapper.MapCourseEvaluationMapper;

import java.util.List;
import java.util.Map;

public interface ResultService {

    public Map<String, Result> getIndexYearResult(String schoolYear);

    public Map<String, Result> getIndexGradeResult(String grade);

}
