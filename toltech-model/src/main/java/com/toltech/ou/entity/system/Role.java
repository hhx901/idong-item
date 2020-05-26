package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 15:08
 * @Version 1.0
 */
@Data
@TableName("sys_role")
@Excel("角色信息表")
public class Role extends BaseEntity{
    private static final long serialVersionUID = -6192233498677160073L;

    @TableId(value = "role_id",type = IdType.AUTO)
    private Long roleId;

    @TableField("role_name")
    @ExcelField(value = "角色名称")
    private String roleName;

    @TableField("role_key")
    @ExcelField(value = "角色权限")
    private String roleKey;

    @TableField("role_sort")
    private String roleSort;
    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    @TableField("data_scope")
    private String dataScope;

    /** 角色状态（0正常 1停用） */
    @TableField("status")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @TableField("role_sort")
    private String delFlag;

    /** 用户是否存在此角色标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    /** 菜单组 */
    @TableField(exist = false)
    private Long[] menuIds;

    /** 部门组（数据权限） */
    @TableField(exist = false)
    private Long[] deptIds;

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Role() {

    }
}
