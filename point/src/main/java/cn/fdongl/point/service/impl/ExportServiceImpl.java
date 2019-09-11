package cn.fdongl.point.service.impl;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.MapCourseEvaluation;
import cn.fdongl.point.service.ExportService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExportServiceImpl
 * @Description TODO
 * @Author zm
 * @Date 2019/9/11 20:31
 * @Version 1.0
 **/
public class ExportServiceImpl implements ExportService {
    /**
     * 导出2学年的信息
     *
     * @param user              当前用户信息
     * @param complexParam      String1：指标大类(1、2、3、4、5)
     *                          String2：指标点小类名 + 指标点小项说明
     *                          List<MapCourseEvaluation>：课程指标点关联项
     */
    @Override
    public void exportResultByTwoYear(JwtUser user, Map<String, Map<String, List<MapCourseEvaluation>>> complexParam) {

    }

    /**
     * 导出一届的信息
     *
     * @param user         当前用户信息
     * @param complexParam
     */
    @Override
    public void exportNormalResult(JwtUser user, Map<String, Map<String, List<MapCourseEvaluation>>> complexParam) {

    }
}
