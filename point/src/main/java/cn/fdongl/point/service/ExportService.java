package cn.fdongl.point.service;

import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ExportService {

    /**
     * 导出2学年的信息
     *
     * @param user 当前用户信息
     */
    void exportExcelResult(
            JwtUser user,
            Map<String, Result> stringResultMap,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException;
}