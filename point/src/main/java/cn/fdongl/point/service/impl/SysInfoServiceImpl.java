package cn.fdongl.point.service.impl;

import cn.fdongl.point.entity.SysDictType;
import cn.fdongl.point.mapper.SysDictMapper;
import cn.fdongl.point.mapper.SysDictTypeMapper;
import cn.fdongl.point.service.SysInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysInfoServiceImpl
 * @Description TODO
 * @Author zm
 * @Date 2019/9/9 12:13
 * @Version 1.0
 **/
@Service
public class SysInfoServiceImpl implements SysInfoService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    /**
     * 获取所有学院信息
     *
     * @return java.util.List<java.lang.String>
     * @author zm
     * @date 2019/9/9 12:12
     **/
    @Override
    public List<String> getDepartment() {
      /*  String departmentTypeId = sysDictTypeMapper.selectIdByTypeName("学院机构");
        List<String> departmentNameList = sysDictMapper
                new ArrayList<>();
        for (String tmpDepartmentId : departmentIdList) {
            departmentNameList.add(sysDictMapper.selectByPrimaryKey(tmpDepartmentId).getDictName());
        }
        return departmentNameList;*/
      return null;
    }
}
