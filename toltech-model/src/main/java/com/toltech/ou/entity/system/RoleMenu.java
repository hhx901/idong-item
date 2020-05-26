package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 15:38
 * @Version 1.0
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu implements Serializable{

    private static final long serialVersionUID = 5457908717826962059L;
    @TableField("role_id")
    private Long roleId;

    @TableField("menu_id")
    private Long menuId;
}
