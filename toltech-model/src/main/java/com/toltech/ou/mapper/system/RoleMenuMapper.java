package com.toltech.ou.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.system.RoleMenu;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/6/21 11:01
 * @Version 1.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu>{
    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量删除角色菜单关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleMenu(Long[] ids);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    public int batchRoleMenu(List<RoleMenu> roleMenuList);
}
