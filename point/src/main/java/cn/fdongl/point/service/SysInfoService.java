package cn.fdongl.point.service;

import java.util.List;

/**
 * 获取系统信息的服务接口
 */
public interface SysInfoService {
    /**
     * 获取所有学院信息
     *
     * @author zm
     * @param []
     * @return java.util.List<java.lang.String>        
     * @date 2019/9/9 12:12
     **/
    List<String> getDepartment();
}