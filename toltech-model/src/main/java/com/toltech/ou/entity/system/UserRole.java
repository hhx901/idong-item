package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 15:35
 * @Version 1.0
 */
@TableName("sys_user_role")
@Data
public class UserRole implements Serializable{

    private static final long serialVersionUID = 4217195784576223619L;
    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;
}
