package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.SysDict;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface SysDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    List<String> selectDictNameByTypeId(@Param("typeId") String typeId);

    /**
     * 获取最近的培养矩阵的期数
     *
     * @author zm
     * @return int
     * @date 2019/9/10 9:39
     **/
    int selectRecentSort();

    /**
     * 字典表中的 period 期数 + 1
     *
     * @author zm
     * @return void        
     * @date 2019/9/10 14:00
     **/
    int periodAddOne();
}