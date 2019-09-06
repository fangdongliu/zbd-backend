package cn.fdongl.authority.service;

import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.vo.SysUser;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUser1Mapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysUser1Mapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysUser record) {
        return sysUser1Mapper.insert(record);
    }

    
    public int insertSelective(SysUser record) {
        return sysUser1Mapper.insertSelective(record);
    }

    
    public SysUser selectByPrimaryKey(String id) {
        return sysUser1Mapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUser1Mapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysUser record) {
        return sysUser1Mapper.updateByPrimaryKey(record);
    }

}
