package cn.fdongl.authority.service;


import cn.fdongl.authority.mapper.SysPowerMapper;
import cn.fdongl.authority.vo.SysPower;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPowerService{

    @Resource
    private SysPowerMapper sysPowerMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysPowerMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysPower record) {
        return sysPowerMapper.insert(record);
    }

    
    public int insertSelective(SysPower record) {
        return sysPowerMapper.insertSelective(record);
    }

    
    public SysPower selectByPrimaryKey(String id) {
        return sysPowerMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysPower record) {
        return sysPowerMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysPower record) {
        return sysPowerMapper.updateByPrimaryKey(record);
    }

}
