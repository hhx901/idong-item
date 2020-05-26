package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.toltech.ou.common.assist.Ztree;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.common.utils.TreeUtils;

import com.toltech.ou.entity.system.Menu;
import com.toltech.ou.entity.system.Role;
import com.toltech.ou.entity.system.User;
import com.toltech.ou.mapper.system.MenuMapper;
import com.toltech.ou.mapper.system.RoleMenuMapper;
import com.toltech.ou.service.system.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.MessageFormat;
import java.util.*;

/**
 * @Author: ouyang
 * @Date: 2019/6/19 15:20
 * @Version 1.0
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements MenuService{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> selectMenusByUser(User user) {
        List<Menu> menus = new LinkedList<Menu>();
       //  管理员显示所有菜单信息
        if (user.isAdmin()) {
            menus = menuMapper.selectMenuNormalAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return TreeUtils.getChildPerms(menus, 0);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        List<Menu> menuList = null;
        User user = ShiroUtils.getSysUser();
        if (user.isAdmin()) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", user.getUserId());
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    @Override
    public List<Menu> selectMenuAll() {
        List<Menu> menuList = null;
        User user = ShiroUtils.getSysUser();
        if (user.isAdmin()) {
            menuList = menuMapper.selectMenuAll();
        } else {
            menuList = menuMapper.selectMenuAllByUserId(user.getUserId());
        }
        return menuList;
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Ztree> roleMenuTreeData(Role role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<Menu> menuList = selectMenuAll();
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    @Override
    public List<Ztree> menuTreeData() {
        List<Menu> menuList = selectMenuAll();
        List<Ztree> ztrees = initZtree(menuList);
        return ztrees;
    }

    @Override
    public Map<String, String> selectPermsAll() {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<Menu> permissions = selectMenuAll();
        if (StringUtils.isNotEmpty(permissions)) {
            for (Menu menu : permissions) {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (Menu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(Menu menu, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    @Override
    public int deleteMenuById(Long menuId) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuMapper.deleteMenuById(menuId);
    }

    @Override
    public Menu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    @Override
    public int insertMenu(Menu menu) {
        menu.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuMapper.insertMenu(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        menu.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuMapper.updateMenu(menu);
    }

    @Override
    public String checkMenuNameUnique(Menu menu) {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }
}
