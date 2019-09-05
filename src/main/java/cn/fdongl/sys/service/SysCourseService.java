package cn.fdongl.sys.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.fdongl.sys.entity.SysCourse;
import cn.fdongl.sys.mapper.SysCourseMapper;
@Service
public class SysCourseService{

    @Resource
    private SysCourseMapper sysCourseMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysCourseMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysCourse record) {
        return sysCourseMapper.insert(record);
    }

    
    public int insertSelective(SysCourse record) {
        return sysCourseMapper.insertSelective(record);
    }

    
    public SysCourse selectByPrimaryKey(String id) {
        return sysCourseMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysCourse record) {
        return sysCourseMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysCourse record) {
        return sysCourseMapper.updateByPrimaryKey(record);
    }

}
