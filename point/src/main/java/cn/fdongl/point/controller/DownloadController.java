package cn.fdongl.point.controller;

import cn.fdongl.authority.util.BaseController;
import cn.fdongl.authority.vo.JwtUser;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.Result;
import cn.fdongl.point.entity.SysFile;
import cn.fdongl.point.entity.TwoString;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysFileMapper;
import cn.fdongl.point.service.ExportService;
import cn.fdongl.point.service.ResultService;
import cn.fdongl.point.util.ExcelUtils;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/download")
public class DownloadController extends BaseController {
    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private ExportService exportService;
    @Autowired
    private ResultService resultService;

    /**
     * 下载往期教师评价表
     *
     * @param id
     * @return
     */
    @GetMapping(value = "downOldTeacherEvaluation", produces = MediaType.ALL_VALUE)
    public void oldTeacherEvaluation(@RequestParam("id") String id,
                                     HttpServletResponse response) throws UnsupportedEncodingException {
        MapTeacherCourse mapTeacherCourse = mapTeacherCourseMapper.selectByPrimaryKey(id);
        String fileId = mapTeacherCourse.getFileId();
        SysFile file = sysFileMapper.selectByPrimaryKey(fileId);

        String fileName = file.getFileName();
        String filePath = file.getFilePath();
        //下载文件
        if (fileName != null) {
            File file1 = new File(filePath, fileName);
            if (file1.exists()) {
                response.setContentType("application/msexcel");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                try {
                    InputStream in = new FileInputStream(file1);
                    ServletOutputStream out = response.getOutputStream();
                    final byte[] b = new byte[8192];
                    for (int r; (r = in.read(b)) != -1; ) {
                        out.write(b, 0, r);
                    }
                    in.close();
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载与该用户所有有关的表
     */
    @GetMapping(value = "downloadUserTable", produces = MediaType.ALL_VALUE)
    public void userTable(@RequestParam("id") String id,
                          HttpServletResponse response) throws UnsupportedEncodingException {
        SysFile file = sysFileMapper.selectByPrimaryKey(id);

        String fileName = file.getFileName();
        String filePath = file.getFilePath();
        //下载文件
        if (fileName != null) {
            File file1 = new File(filePath, fileName);
            if (file1.exists()) {
                response.setContentType("application/msexcel");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                try {
                    InputStream in = new FileInputStream(file1);
                    ServletOutputStream out = response.getOutputStream();
                    final byte[] b = new byte[8192];
                    for (int r; (r = in.read(b)) != -1; ) {
                        out.write(b, 0, r);
                    }
                    in.close();
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("exportTest")
    public void exportTest(
            JwtUser jwtUser,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        Map<String, Result> stringResult = new HashMap<>();
        Result tmpResult = new Result();

        List<String> indexList = new ArrayList<>();
        indexList.add("7.1能够了解软件工程及相关行业的政策和法律法规势");
        indexList.add("7.2能够理解复杂软件工程问题的专业实践和对环境以及社会可持续的影响");
        List<String> courseList = new ArrayList<>();
        courseList.add("思想道德与法律基础2014-2015学年");
        courseList.add("思想道德与法律基础2015-2016学年");
        courseList.add("知识产权法基础2014-2015学年");
        courseList.add("知识产权法基础2015-2016学年");
        courseList.add("大类专业导论2014-2015学年");
        courseList.add("大类专业导论2015-2016学年");
        courseList.add("互联网应用开发基础训练2014-2015学年");
        courseList.add("互联网用用开发基础训练2015-2016学年");
        courseList.add("毕业设计(论文)2014-2015学年");
        courseList.add("毕业设计(论文)2015-2016学年");
        Map<TwoString, String> relation = new HashMap<>();
        relation.put(new TwoString("思想道德与法律基础2014-2015学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.3");
        relation.put(new TwoString("思想道德与法律基础2015-2016学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.3");
        relation.put(new TwoString("知识产权法基础2014-2015学年", "7.2能够理解复杂软件工程问题的专业实践和对环境以及社会可持续的影响"), "0.4");
        relation.put(new TwoString("知识产权法基础2015-2016学年", "7.2能够理解复杂软件工程问题的专业实践和对环境以及社会可持续的影响"), "0.4");

        relation.put(new TwoString("大类专业导论2014-2015学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.3");
        relation.put(new TwoString("大类专业导论2015-2016学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.3");
        relation.put(new TwoString("互联网应用开发基础训练2014-2015学年", "7.2能够理解复杂软件工程问题的专业实践和对环境以及社会可持续的影响"), "0.6");
        relation.put(new TwoString("互联网用用开发基础训练2015-2016学年", "7.2能够理解复杂软件工程问题的专业实践和对环境以及社会可持续的影响"), "0.6");
        relation.put(new TwoString("毕业设计(论文)2014-2015学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.4");
        relation.put(new TwoString("毕业设计(论文)2015-2016学年", "7.1能够了解软件工程及相关行业的政策和法律法规势"), "0.4");

        tmpResult.setIndexList(indexList);
        tmpResult.setCourseList(courseList);
        tmpResult.setRelation(relation);

        Map<String, Result> sr = new HashMap<>();
        sr.put("7", tmpResult);
        try {
            exportService.exportExcelResult(jwtUser, sr, httpServletRequest, httpServletResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 按年导出指标点excel结果
     *
     * @param schoolYear
     * @return void
     * @author zm
     * @date 2019/9/12 0:21
     **/
    @GetMapping("bySchoolYear")
    public void exportByTwoYear(
            JwtUser jwtUser,
            @RequestParam("schoolYear") String schoolYear,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        Map<String, Result> resultMap = resultService.getIndexGradeResult(schoolYear);
        try {
            exportService.exportExcelResult(jwtUser, resultMap, httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按学级导出指标点excel结果
     *
     * @param grade 年级
     * @return void
     * @author zm
     * @date 2019/9/12 0:21
     **/
    @GetMapping("byGrade")
    public void exportByGrade(
            JwtUser jwtUser,
            @RequestParam("grade") String grade,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        Map<String, Result> resultMap = resultService.getIndexGradeResult(grade);
        try {
            exportService.exportExcelResult(jwtUser, resultMap, httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
