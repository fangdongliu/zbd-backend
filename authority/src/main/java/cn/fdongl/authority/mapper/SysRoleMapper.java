package cn.fdongl.authority.mapper;


import cn.fdongl.authority.vo.SysRole;

public interface SysRoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据角色码roleCode获取指定的roleId
     *
     * @author zm
     * @param roleCode
     * @return java.lang.String
     * @date 2019/9/9 15:05
     **/
    String selectIdByRoleCode(String roleCode);
}