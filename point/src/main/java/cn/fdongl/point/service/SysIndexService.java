package cn.fdongl.point.service;


import cn.fdongl.point.dao.SysIndexMapper;
import cn.fdongl.point.entity.SysIndex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysIndexService {

    @Resource
    private SysIndexMapper sysIndexMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysIndexMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysIndex record) {
        return sysIndexMapper.insert(record);
    }

    
    public int insertSelective(SysIndex record) {
        return sysIndexMapper.insertSelective(record);
    }

    
    public SysIndex selectByPrimaryKey(String id) {
        return sysIndexMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysIndex record) {
        return sysIndexMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysIndex record) {
        return sysIndexMapper.updateByPrimaryKey(record);
    }

}
