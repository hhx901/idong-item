package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.exception.BusinessException;
import com.toltech.ou.common.tag.DataScope;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.SpringUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.Role;
import com.toltech.ou.entity.system.RoleDept;
import com.toltech.ou.entity.system.RoleMenu;
import com.toltech.ou.entity.system.UserRole;
import com.toltech.ou.mapper.system.RoleDeptMapper;
import com.toltech.ou.mapper.system.RoleMapper;
import com.toltech.ou.mapper.system.RoleMenuMapper;
import com.toltech.ou.mapper.system.UserRoleMapper;
import com.toltech.ou.service.system.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: ouyang
 * @Date: 2019/6/19 15:05
 * @Version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleDeptMapper roleDeptMapper;

    @Override
    @DataScope(deptAlias = "d")
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<Role> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        List<Role> userRoles = roleMapper.selectRolesByUserId(userId);
        List<Role> roles = selectRoleAll();
        for (Role role : roles) {
            for (Role userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<Role> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new Role());
    }

    @Override
    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public boolean deleteRoleById(Long roleId) {
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    @Override
    public int deleteRoleByIds(String ids) throws Exception {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new Role(roleId));
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    @Override
    @Transactional
    public int insertRole(Role role) {
        role.setCreateBy(ShiroUtils.getLoginName());
        // 新增角色信息
        roleMapper.insertRole(role);
        ShiroUtils.clearCachedAuthorizationInfo();
        return insertRoleMenu(role);
    }
    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    @Override
    public int updateRole(Role role) {
        role.setUpdateBy(ShiroUtils.getLoginName());
        // 修改角色信息
        roleMapper.updateRole(role);
        ShiroUtils.clearCachedAuthorizationInfo();
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    @Override
    public int authDataScope(Role role) {
        role.setUpdateBy(ShiroUtils.getLoginName());
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(Role role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<RoleDept> list = new ArrayList<RoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            RoleDept rd = new RoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    @Override
    public String checkRoleNameUnique(Role role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(Role role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    @Override
    public void checkRoleAllowed(Role role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new BusinessException("不允许操作超级管理员角色");
        }
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    @Override
    public int changeStatus(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int deleteAuthUser(UserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long userId : users)
        {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}

