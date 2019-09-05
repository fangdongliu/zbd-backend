package cn.fdongl.log.service;

import cn.fdongl.log.entity.SysLogAction;
import cn.fdongl.log.mapper.SysLogActionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogActionService{

    @Resource
    private SysLogActionMapper sysLogActionMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysLogActionMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysLogAction record) {
        return sysLogActionMapper.insert(record);
    }

    
    public int insertSelective(SysLogAction record) {
        return sysLogActionMapper.insertSelective(record);
    }

    
    public SysLogAction selectByPrimaryKey(String id) {
        return sysLogActionMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysLogAction record) {
        return sysLogActionMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysLogAction record) {
        return sysLogActionMapper.updateByPrimaryKey(record);
    }

}
