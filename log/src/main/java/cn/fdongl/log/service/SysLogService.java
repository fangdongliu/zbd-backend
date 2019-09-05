package cn.fdongl.log.service;


import cn.fdongl.log.entity.SysLog;
import cn.fdongl.log.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogService{

    @Resource
    private SysLogMapper sysLogMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysLogMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysLog record) {
        return sysLogMapper.insert(record);
    }

    
    public int insertSelective(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }

    
    public SysLog selectByPrimaryKey(String id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysLog record) {
        return sysLogMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysLog record) {
        return sysLogMapper.updateByPrimaryKey(record);
    }

}
