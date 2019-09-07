package cn.fdongl.authority.service;

import cn.fdongl.authority.mapper.SysUserMapper;
import cn.fdongl.authority.vo.SysUser;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    
    public int deleteByPrimaryKey(String id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    
    public SysUser selectByPrimaryKey(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    public List<SysUser> selectPageWithCondition(SysUser userSearch) {
        return sysUserMapper.selectPageWithCondition(userSearch);
    }

    public int selectNumWithCondition(SysUser userSearch) {
        return sysUserMapper.selectNumWithCondition(userSearch);
    }

    public int deleteByIds(List<SysUser> userList){
        return sysUserMapper.deleteByIds(userList);
    }
}
