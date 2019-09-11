package cn.fdongl.point.service.impl;

import cn.fdongl.point.entity.MapCourseEvaluation;
import cn.fdongl.point.entity.MapTeacherCourse;
import cn.fdongl.point.entity.SysFile;
import cn.fdongl.point.entity.SysIndex;
import cn.fdongl.point.mapper.MapCourseEvaluationMapper;
import cn.fdongl.point.mapper.MapTeacherCourseMapper;
import cn.fdongl.point.mapper.SysIndexMapper;
import cn.fdongl.point.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultServiceImpl implements ResultService {

    @Autowired
    private MapTeacherCourseMapper mapTeacherCourseMapper;

    @Autowired
    private MapCourseEvaluationMapper mapCourseEvaluationMapper;

    @Autowired
    private SysIndexMapper sysIndexMapper;

    @Override
    public Map<String, Map<String, List<MapCourseEvaluation>>> getIndexYearResult(String schoolYear) {
        String[] years=schoolYear.split("~");
        //将选择的两学年转换为学期
        int first=Integer.getInteger(years[0]);
        int second=Integer.getInteger(years[1]);
        List<String> semesters=new ArrayList<>();
        for(int i=0;i<4;i++){
            String t=null;
            switch (i){
                case 0:t=String.valueOf(first)+"-"+String.valueOf(second)+"-1";break;
                case 1:t=String.valueOf(first)+"-"+String.valueOf(second)+"-2";break;
                case 2:t=String.valueOf(second)+"-"+String.valueOf(second+1)+"-1";break;
                case 3:t=String.valueOf(second)+"-"+String.valueOf(second+1)+"-2";break;
            }
            semesters.add(t);
        }
        //获取教师关联课程中的所有学期的课程id
        List<String> courseId=mapTeacherCourseMapper.selectIdBySemesters(semesters);
        //根据courseId和指标点获取所有的指标点评价值
        List<MapCourseEvaluation> mapCourseEvaluations=mapCourseEvaluationMapper.getByCourseIdAndIndex(courseId);
        for(int i=0;i<mapCourseEvaluations.size();i++){
            String indexId=mapCourseEvaluations.get(i).getIndexId();//获取指标点id
            SysIndex index=sysIndexMapper.selectByPrimaryKey(indexId);
            mapCourseEvaluations.get(i).setSysIndex(index);
        }
        //合并课程编号相同的指标点
        List<MapCourseEvaluation> results=getDifEvaluation(mapCourseEvaluations);
        //完成评价值，子指标点，父指标点的映射

        return null;
    }

    @Override
    public Map<String, Map<String, List<MapCourseEvaluation>>> getIndexGradeResult(String grade) {
        return null;
    }


    //合并相同指标点的课程评价
    public List<MapCourseEvaluation> getDifEvaluation(List<MapCourseEvaluation> mapCourseEvaluations){
        Map<String,MapCourseEvaluation> map=new HashMap<>();
        for(int i=0;i<mapCourseEvaluations.size();i++){
            MapCourseEvaluation mapCourseEvaluation=mapCourseEvaluations.get(i);
            String courseId=mapCourseEvaluations.get(i).getCourseId();
            MapTeacherCourse mapTeacherCourse=mapTeacherCourseMapper.selectByPrimaryKey(courseId);
            String courseNum=mapTeacherCourse.getCourseNumber();
            mapCourseEvaluation.setMapTeacherCourse(mapTeacherCourse);
            if(!map.containsKey(courseNum)){
                //不包含该课程编号
                map.put(courseNum,mapCourseEvaluation);
            }else{
                //包含该课程编号，选择小的添加
                MapCourseEvaluation m=map.get(courseNum);
                Double v=m.getEvaluationValue();
                if(v>mapCourseEvaluation.getEvaluationValue()){
                    //当前指标点要小的话就要替换
                    map.put(courseNum,mapCourseEvaluation);
                }
            }
        }
        List<MapCourseEvaluation> results=new ArrayList<>();
        for(MapCourseEvaluation value : map.values()){
            results.add(value);
        }
        return results;
    }

    //将评价值按照父指标点归类
    public Map<String,Map<String,List<MapCourseEvaluation>>> getParentMap(List<MapCourseEvaluation> list){
        Map<String,Map<String,List<MapCourseEvaluation>>> map=new HashMap<>();
//        for(int i=0;i<list.size();i++){
//            String parentTitle=list.get(i).getSysIndex().getIndexTitle();
//            Map<String,List<MapCourseEvaluation>> m=null;
//            MapCourseEvaluation t=list.get(i);
//            if(!map.containsKey(parentTitle)){
//                //不包含该父title
//                m=new HashMap<>();
//                List<MapCourseEvaluation> l=new ArrayList<>();
//                l.add(list.get(i));
//                m.put(list.get(i).getIndexNumber()+)
//                map.put(parentTitle,l);
//            }else{
//                //包含该父title
//                l=map.get(parentTitle);
//                l.add(list.get(i));
//            }
//        }
        return map;
    }
}
