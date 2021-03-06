package cn.fdongl.point.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UploadStatusMapper {

    @Insert("insert into upload_status values(#{param1},#{param2})")
    int insert(String id, int status);

    @Update("update upload_status set `status` = #{param2} where id = #{param1}")
    int update(String id, int status);

    @Select("select `status` from upload_status where id = #{param1}")
    Integer getStatus(String id);

    @Update("update map_student_course set complete = 1 where id = #{param1}")
    Integer updateStudentCourseStatus(String id);

    @Update("update teacher_student_course set status = 4 where id = #{param1}")
    Integer updateTeacherCourseStatus(String id);
}
