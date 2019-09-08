package cn.fdongl.point.mapper;

import cn.fdongl.point.entity.MapCultivateFile;

public interface MapCultivateFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(MapCultivateFile record);

    int insertSelective(MapCultivateFile record);

    MapCultivateFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MapCultivateFile record);

    int updateByPrimaryKey(MapCultivateFile record);
}