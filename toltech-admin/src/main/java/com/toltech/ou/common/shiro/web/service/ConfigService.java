package com.toltech.ou.common.shiro.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 10:59
 * @Version 1.0
 */
@Service("config")
public class ConfigService {
    @Autowired
    private com.toltech.ou.service.system.ConfigService configService;

    /**
     * 根据键名查询参数配置信息
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.selectConfigByKey(configKey);
    }

}
