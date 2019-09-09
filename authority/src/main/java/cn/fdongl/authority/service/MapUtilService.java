package cn.fdongl.authority.service;

import cn.fdongl.authority.mapper.MapUserRoleMapper;
import cn.fdongl.authority.mapper.SysRoleMapper;
import cn.fdongl.authority.vo.MapUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MapUtilService
 * @Description 关联表业务类
 * @Author zm
 * @Date 2019/9/9 15:11
 * @Version 1.0
 **/
@Service
public class MapUtilService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private MapUserRoleMapper mapUserRoleMapper;

    private MapUserRole mapUserRole = new MapUserRole();

    /**
     * 插入用户角色关联项
     *
     * @author zm
     * @param userId 用户主键id
     * @param roleCode 角色代码(student/teacher/admin...)
     * @return void        
     * @date 2019/9/9 15:15
     **/
    public void addNewRoleMap(String userId,String roleCode){
        String roleId = sysRoleMapper.selectIdByRoleCode(roleCode);
        mapUserRole.setUUId();
        mapUserRole.setUserId(userId);
        mapUserRole.setRoleId(roleId);
        mapUserRoleMapper.insertSelective(mapUserRole);
    }
}
