package com.toltech.ou.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.system.RoleDept;
import com.toltech.ou.entity.system.RoleMenu;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 14:01
 * @Version 1.0
 */
public interface RoleDeptMapper extends BaseMapper<RoleDept> {
    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * 查询部门使用数量
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
    public int batchRoleDept(List<RoleDept> roleDeptList);
}
