package cn.fdongl.sys.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.fdongl.sys.entity.SysDictType;
import cn.fdongl.sys.mapper.SysDictTypeMapper;
@Service
public class SysDictTypeService{

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysDictTypeMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysDictType record) {
        return sysDictTypeMapper.insert(record);
    }

    
    public int insertSelective(SysDictType record) {
        return sysDictTypeMapper.insertSelective(record);
    }

    
    public SysDictType selectByPrimaryKey(String id) {
        return sysDictTypeMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysDictType record) {
        return sysDictTypeMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysDictType record) {
        return sysDictTypeMapper.updateByPrimaryKey(record);
    }

}
