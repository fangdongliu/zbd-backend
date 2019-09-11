package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.MapCourseEvaluation;
import cn.fdongl.point.entity.SysIndex;

import java.util.List;
import java.util.Map;

public interface ExportService {

    /**
     * 导出2学年的信息
     * @param user 当前用户信息
     */
    void exportResultByTwoYear(JwtUser user, Map<String, Map<String, List<MapCourseEvaluation>>> complexParam);

    /**
     * 导出一届的信息
     * @param user 当前用户信息·1
     */
    void exportNormalResult(JwtUser user, Map<String, Map<String, List<MapCourseEvaluation>>> complexParam);

}
