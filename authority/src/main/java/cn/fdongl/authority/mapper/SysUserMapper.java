package cn.fdongl.authority.mapper;


import cn.fdongl.authority.vo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface SysUserMapper {
    /**
     * @param userName 用户名
     * @return 查找用户的所有数据，二次查询(基础信息+角色+权限)
     */
    SysUser findUserByUserName(String userName);

    /**
     * @param sysUser 用户名
     * @return 根据Page分页查询用户List
     */
    List<SysUser> selectPageWithCondition(SysUser sysUser);

    /**
     * @param sysUser 用户名
     * @return 根据Page分页查询用户数目
     */
    int selectNumWithCondition(SysUser sysUser);

    /**
     * @param userList 用户列表
     * @return 根据主键批量删除用户
     */
    int deleteByIds(List<SysUser> userList);

    /**
     * 获取所有用户的List
     *
     * @author zm
     * @param
     * @return java.util.List<java.lang.String>
     * @date 2019/9/8 13:48
     **/
    List<String> selectAllUser();

    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    /**
     * @param id 用户id
     * @return 返回待查询用户(status 不为 -1)
     **/
    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int insertBatch(List<SysUser> record);

    int updateByPrimaryKey(SysUser record);
}