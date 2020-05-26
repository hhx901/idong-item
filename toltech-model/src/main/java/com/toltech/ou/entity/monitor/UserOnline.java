package com.toltech.ou.entity.monitor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toltech.ou.common.assist.OnlineSession;
import com.toltech.ou.common.enums.OnlineStatus;
import com.toltech.ou.entity.Base.BaseEntity;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 8:44
 * @Version 1.0
 */
@Data
@Excel("当前在线会话")
@TableName("sys_user_online")
public class UserOnline implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户会话id */
    @TableId(value = "sessionId" ,type = IdType.AUTO)
    private String sessionId;

    /** 部门名称 */
    @TableField("dept_name")
    private String deptName;

    /** 登录名称 */
    @TableField("login_name")
    private String loginName;

    /** 登录IP地址 */
    @TableField("ipaddr")
    private String ipaddr;

    /** 登录地址 */
    @TableField("login_location")
    private String loginLocation;

    /** 浏览器类型 */
    @TableField("browser")
    private String browser;

    /** 操作系统 */
    @TableField("os")
    private String os;

    /** session创建时间 */
    @TableField("start_timestamp")
    private Date startTimestamp;

    /** session最后访问时间 */
    @TableField("last_access_time")
    private Date lastAccessTime;

    /** 超时时间，单位为分钟 */
    @TableField("expire_time")
    private Long expireTime;

    /** 在线状态 */
    @TableField("status")
    private OnlineStatus status=OnlineStatus.on_line;
    /** 备份的当前用户会话 */
    @TableField(exist = false)
    private OnlineSession session;


}
