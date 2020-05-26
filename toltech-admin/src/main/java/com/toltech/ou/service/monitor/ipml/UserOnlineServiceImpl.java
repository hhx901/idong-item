package com.toltech.ou.service.monitor.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.utils.DateUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.monitor.UserOnline;
import com.toltech.ou.mapper.monitor.UserOnlineMapper;
import com.toltech.ou.service.monitor.UserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:26
 * @Version 1.0
 */
@Service
public class UserOnlineServiceImpl extends ServiceImpl<UserOnlineMapper,UserOnline> implements UserOnlineService {

    @Autowired
    private UserOnlineMapper userOnlineMapper;

    @Override
    public UserOnline selectOnlineById(String sessionId) {
        return userOnlineMapper.selectOnlineById(sessionId);
    }

    @Override
    public void deleteOnlineById(String sessionId) {
        UserOnline userOnline = selectOnlineById(sessionId);
        if (StringUtils.isNotNull(userOnline))
        {
            userOnlineMapper.deleteOnlineById(sessionId);
        }
    }

    @Override
    public void batchDeleteOnline(List<String> sessions) {
        for (String sessionId : sessions) {
            UserOnline userOnline = selectOnlineById(sessionId);
            if (StringUtils.isNotNull(userOnline)) {
                userOnlineMapper.deleteOnlineById(sessionId);
            }
        }
    }

    @Override
    public void saveOnline(UserOnline online) {
        userOnlineMapper.saveOnline(online);
    }

    @Override
    public List<UserOnline> selectUserOnlineList(UserOnline userOnline) {
        return userOnlineMapper.selectUserOnlineList(userOnline);
    }

    @Override
    public void forceLogout(String sessionId) {
//        Session session = onlineSessionDAO.readSession(sessionId);
//        if (session == null)
//        {
//            return;
//        }
//        session.setTimeout(1000);
//        userOnlineMapper.deleteOnlineById(sessionId);
    }

    @Override
    public List<UserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
    }
}
