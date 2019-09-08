package cn.fdongl.point.entity;

import lombok.Data;

@Data
public class MapCultivateFile {
    /**
    * 主键
    */
    private String id;

    /**
    * 文件id
    */
    private String fileId;

    /**
    * 学院名称
    */
    private String college;

    /**
    * 培养方案名称
    */
    private String cultivateName;

    /**
    * 所对应的年级
    */
    private String grade;
}