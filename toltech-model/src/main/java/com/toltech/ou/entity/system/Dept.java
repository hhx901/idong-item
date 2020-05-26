package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 14:59
 * @Version 1.0
 */
@Data
@TableName("sys_dept")
@Excel("部门信息表")
public class Dept extends BaseEntity{

    private static final long serialVersionUID = -1930909566501300573L;
    @TableId(value = "dept_id",type = IdType.AUTO)
    private Long deptId;

    @TableField("parent_id")
    private Long parentId;

    @TableField("dept_name")
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    @TableField("order_num")
    @NotBlank(message = "显示顺序不能为空")
    private Double orderNum;
    /** 祖级列表 */
    @TableField("ancestors")
    private String ancestors;

    /** 负责人 */
    @TableField("leader")
    private String leader;

    /** 联系电话 */
    @TableField("phone")
    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /** 邮箱 */
    @TableField("email")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /** 部门状态:0正常,1停用 */
    @TableField("status")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @TableField("del_flag")
    private String delFlag;

    /** 父部门名称 */
    @TableField("parent_name")
    private String parentName;
}
