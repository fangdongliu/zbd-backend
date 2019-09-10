package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    List<SysFile> selectAllFile();

    List<SysFile> getSysFileByPage(SysFile file);

    int getTotal(SysFile file);
}