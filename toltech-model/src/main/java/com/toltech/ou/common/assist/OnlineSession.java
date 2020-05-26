package com.toltech.ou.common.assist;

import com.toltech.ou.common.enums.OnlineStatus;
import lombok.Data;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 8:54
 * @Version 1.0
 */
@Data
public class OnlineSession extends SimpleSession {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户名称 */
    private String loginName;

    /** 部门名称 */
    private String deptName;

    /** 用户头像 */
    private String avatar;

    /** 登录IP地址 */
    private String host;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 在线状态 */
    private OnlineStatus status = OnlineStatus.on_line;

    /** 属性是否改变 优化session数据同步 */
    private transient boolean attributeChanged = false;

    @Override
    public String getHost()
    {
        return host;
    }

    @Override
    public void setHost(String host)
    {
        this.host = host;
    }
    public void resetAttributeChanged() {
        this.attributeChanged = false;
    }

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }

}
