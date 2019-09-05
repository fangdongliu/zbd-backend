package cn.fdongl.sys.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.fdongl.sys.mapper.SysCreditMapper;
import cn.fdongl.sys.entity.SysCredit;
@Service
public class SysCreditService{

    @Resource
    private SysCreditMapper sysCreditMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysCreditMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysCredit record) {
        return sysCreditMapper.insert(record);
    }

    
    public int insertSelective(SysCredit record) {
        return sysCreditMapper.insertSelective(record);
    }

    
    public SysCredit selectByPrimaryKey(String id) {
        return sysCreditMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysCredit record) {
        return sysCreditMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysCredit record) {
        return sysCreditMapper.updateByPrimaryKey(record);
    }

}
