package com.toltech.ou.mapper.monitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.monitor.UserOnline;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:28
 * @Version 1.0
 */
public interface UserOnlineMapper extends BaseMapper<UserOnline> {

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public UserOnline selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public int deleteOnlineById(String sessionId);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     * @return 结果
     */
    public int saveOnline(UserOnline online);

    /**
     * 查询会话集合
     *
     * @param userOnline 会话参数
     * @return 会话集合
     */
    public List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * 查询过期会话集合
     *
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    public List<UserOnline> selectOnlineByExpired(String lastAccessTime);
}
