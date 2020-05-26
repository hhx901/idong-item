package com.toltech.ou.service.log.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.entity.log.SysLog;
import com.toltech.ou.mapper.log.SysLogMapper;
import com.toltech.ou.service.log.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 14:50
 * @Version 1.0
 */
@Slf4j
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService{

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void insertLogininfor(SysLog logininfor) {
        sysLogMapper.insertLogininfor(logininfor);
    }

    @Override
    public List<SysLog> selectLogininforList(SysLog logininfor) {
        return sysLogMapper.selectLogininforList(logininfor);
    }

    @Override
    public int deleteLogininforByIds(String ids) {
        return sysLogMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    @Override
    public void cleanLogininfor() {
        sysLogMapper.cleanLogininfor();
    }
}
