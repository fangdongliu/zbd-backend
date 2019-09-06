package cn.fdongl.authority.mapper;


import cn.fdongl.authority.vo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SysUserMapper {
    /**
     * @param userName 用户名
     * @return 查找用户的所有数据，二次查询(基础信息+角色+权限)
     */
    SysUser findUserByUserName(String userName);

    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}