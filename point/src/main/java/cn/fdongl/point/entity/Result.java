package cn.fdongl.point.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Result {
    List<String> indexList;
    List<String> courseList;
    Map<TwoString,String> relation;
}
