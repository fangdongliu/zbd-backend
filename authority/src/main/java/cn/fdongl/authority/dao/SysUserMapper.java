package cn.fdongl.authority.dao;


import cn.fdongl.authority.vo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SysUserMapper {
    /**
     * @param realName 用户名
     * @return 查找用户的所有数据，二次查询(基础信息+角色+权限)
     */
    SysUser findUserByRealName(String realName);

    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}