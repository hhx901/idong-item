package com.toltech.ou.service.system.ipml;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.assist.Ztree;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.exception.BusinessException;
import com.toltech.ou.common.tag.DataScope;


import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.Dept;
import com.toltech.ou.entity.system.Role;
import com.toltech.ou.mapper.system.DeptMapper;
import com.toltech.ou.service.system.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ouyang
 * @Date: 2019/6/20 16:35
 * @Version 1.0
 */
@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper,Dept> implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    @DataScope(deptAlias = "d")
    public List<Dept> selectDeptList(Dept dept) {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(Dept dept) {
        List<Dept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    @Override
    public List<Ztree> roleDeptTreeData(Role role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Dept> deptList, List<String> roleDeptList)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (Dept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Dept> deptList)
    {
        return initZtree(deptList, null);
    }

    @Override
    public int selectDeptCount(Long parentId) {
        Dept dept = new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public int insertDept(Dept dept) {
        Dept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new BusinessException("部门停用，不允许新增");
        }
        dept.setCreateBy(ShiroUtils.getLoginName());
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    @Override
    public int updateDept(Dept dept) {
        Dept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        Dept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<Dept> children = deptMapper.selectChildrenDeptById(deptId);
        for (Dept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId, String ancestors)
    {
        Dept dept = new Dept();
        dept.setParentId(deptId);
        List<Dept> childrens = deptMapper.selectDeptList(dept);
        for (Dept children : childrens)
        {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0)
        {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    @Override
    public Dept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public String checkDeptNameUnique(Dept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }
}
