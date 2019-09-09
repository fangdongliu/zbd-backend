package cn.fdongl.point.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import cn.fdongl.point.entity.MapStudentCourse;

public interface MapStudentCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapStudentCourse record);

    int insertBatch(List<MapStudentCourse>list);

    int insertSelective(MapStudentCourse record);

    MapStudentCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapStudentCourse record);

    int updateByPrimaryKey(MapStudentCourse record);

    MapStudentCourse selectByUserWorkIdAndCourseSelectNumber(
            @Param("userWorkId")String userWorkId,
            @Param("courseSelectNumber")String courseSelectNumber);
}