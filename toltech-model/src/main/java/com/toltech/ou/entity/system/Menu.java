package com.toltech.ou.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/6/18 15:18
 * @Version 1.0
 */
@Data
@TableName("sys_menu")
@Excel("菜单信息表")
public class Menu extends BaseEntity{
    private static final long serialVersionUID = -230819993723387112L;

    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;
    @TableField("menu_name")
    @ExcelField(value = "名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;
    /** 父菜单名称 */
    @TableField("parent_name")
    private String parentName;
    @TableField("parent_id")
    private Long parentId;
    /** 显示顺序 */
    @TableField("order_num")
    @NotBlank(message = "显示顺序不能为空")
    private String orderNum;

    /** 菜单URL */
    @TableField("url")
    @Size(min = 0, max = 200, message = "请求地址不能超过200个字符")
    private String url;

    /** 打开方式：menuItem页签 menuBlank新窗口 */
    @TableField("target")
    private String target;

    /** 类型:0目录,1菜单,2按钮 */
    @TableField("menu_type")
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /** 菜单状态:0显示,1隐藏 */
    @TableField("visible")
    private String visible;

    /** 权限字符串 */
    @TableField("perms")
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /** 菜单图标 */
    @TableField("icon")
    private String icon;

    /** 子菜单 */
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<Menu>();
}
