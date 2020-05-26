package com.toltech.ou.service.monitor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.toltech.ou.entity.monitor.UserOnline;

import java.util.Date;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:25
 * @Version 1.0
 */
public interface UserOnlineService extends IService<UserOnline> {

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
    public void deleteOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    public void batchDeleteOnline(List<String> sessions);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     */
    public void saveOnline(UserOnline online);

    /**
     * 查询会话集合
     *
     * @param userOnline 分页参数
     * @return 会话集合
     */
    public List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    public void forceLogout(String sessionId);

    /**
     * 查询会话集合
     *
     * @param expiredDate 有效期
     * @return 会话集合
     */
    public List<UserOnline> selectOnlineByExpired(Date expiredDate);
}
