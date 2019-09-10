package cn.fdongl.point.mapper;

import cn.fdongl.authority.util.SearchResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import cn.fdongl.point.entity.MapStudentCourse;

public interface MapStudentCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapStudentCourse record);

    int insertBatch(List<MapStudentCourse> list);

    int insertSelective(MapStudentCourse record);

    MapStudentCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapStudentCourse record);

    int updateByPrimaryKey(MapStudentCourse record);

    MapStudentCourse selectByUserWorkIdAndCourseSelectNumber(
            @Param("userWorkId") String userWorkId,
            @Param("courseSelectNumber") String courseSelectNumber);

    /**
     * 逆序获得学生所有学期
     *
     * @param userWorkId
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 15:26
     **/
    List<String> selectDistinctCourseSemesterByUserWorkIdDeOrderByCourseSemester(
            @Param("userWorkId") String userWorkId);

    /**
     * //TODO
     *
     * @param userWorkId
     * @param courseSemester
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/10 16:16
     **/
    List<String> selectCourseSelectNumberPageByUserWorkIdAndCourseSemester(
            @Param("userWorkId") String userWorkId,
            @Param("courseSemester") String courseSemester,
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize);

    /**
     * //TODO
     *
     * @param userWorkId
     * @param courseSemester
     * @param pageStart
     * @param pageSize
     * @return java.util.List<cn.fdongl.authority.util.SearchResult>
     * @author zm
     * @date 2019/9/10 17:04
     **/
    List<SearchResult> selectCourseNameAndCourseSelectNumberPageByUserWorkIdAndCourseSemester(
            @Param("userWorkId") String userWorkId,
            @Param("courseSemester") String courseSemester,
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize);

    int selectSearchResultNumByUserWorkIdAndCourseSemester(
            @Param("userWorkId") String userWorkId,
            @Param("courseSemester") String courseSemester);
}