package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 13:59
 * @Version 1.0
 */
@Data
@TableName("sys_role_dept")
public class RoleDept implements Serializable{
    private static final long serialVersionUID = -3216351599753894139L;

    /** 角色ID */
    @TableField("role_id")
    private Long roleId;

    /** 部门ID */
    @TableField("dept_id")
    private Long deptId;
}
