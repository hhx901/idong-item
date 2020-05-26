package com.toltech.ou.common.enums;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 8:48
 * @Version 1.0
 */
public enum OnlineStatus {
    /** 用户状态 */
    on_line("在线"), off_line("离线");
    private final String info;

    private OnlineStatus(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
