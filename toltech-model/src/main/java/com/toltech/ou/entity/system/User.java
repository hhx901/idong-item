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
import java.util.Date;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 14:20
 * @Version 1.0
 */
@Data
@TableName("sys_user")
@Excel("用户信息表")
public class User extends BaseEntity{
    private static final long serialVersionUID = -4356315621249780114L;

    @TableId(value = "user_id" ,type = IdType.AUTO)
    private Long userId;
    @TableField("dept_id")
    private Long deptId;
    @TableField(exist = false)
    private Long parentId;
    @NotBlank(message = "{required}")
    @TableField(exist = false)
    private String roleId;
    @TableField("login_name")
    private String loginName;
    @TableField("user_name")
    private String userName;
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    @TableField("phonenumber")
    private String phonenumber;
    @TableField("sex")
    private String sex;
    /** 用户头像 */
    @TableField("avatar")
    private String avatar;
    /** 密码 */
    @TableField("password")
    private String password;
    /** 盐加密 */
    @TableField("salt")
    private String salt;
    /** 帐号状态（0正常 1停用） */
    @TableField("status")
    private String status;
    /** 删除标志（0代表存在 2代表删除） */
    @TableField("del_flag")
    private String delFlag;
    /** 最后登陆IP */
    @TableField("login_ip")
    private String loginIp;
    /** 最后登陆时间 */
    @TableField("login_date")
    private Date loginDate;
    /** 部门对象 */
    @TableField(exist = false)
    private Dept dept;
    @TableField(exist = false)
    private List<Role> roles;
    /** 角色组 */
    @TableField(exist = false)
    private Long[] roleIds;
    /** 岗位组 */
    @TableField(exist = false)
    private Long[] postIds;

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }
}
