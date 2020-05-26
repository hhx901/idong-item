package com.toltech.ou.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Maps;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 9:48
 * @Version 1.0
 */
@Data
@TableName("sys_logininfor")
@Excel("系统日志表")
public class SysLog implements Serializable{
    private static final long serialVersionUID = 7448614718168049420L;


    /** ID */
    @TableId(value = "info_id",type = IdType.AUTO)
    private Long infoId;

    /** 用户账号 */
    @TableField("login_name")
    private String loginName;

    /** 登录状态 0成功 1失败 */
    @TableField("status")
    private String status;

    /** 登录IP地址 */
    @TableField("ipaddr")
    private String ipaddr;

    /** 登录地点 */
    @TableField("login_location")
    private String loginLocation;

    /** 浏览器类型 */
    @TableField("browser")
    private String browser;

    /** 操作系统 */
    @TableField("os")
    private String os;

    /** 提示消息 */
    @TableField("msg")
    private String msg;

    /** 访问时间 */
    @TableField("login_time")
    private Date loginTime;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = Maps.newHashMap();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
