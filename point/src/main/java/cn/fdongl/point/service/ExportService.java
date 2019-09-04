package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;

public interface ExportService {

    /**
     * 导出2学年的信息
     * @param user 当前用户信息
     */
    void exportResultByTwoYear(JwtUser user);

    /**
     * 导出一届的信息
     * @param user 当前用户信息
     */
    void exportNormalResult(JwtUser user);

}
