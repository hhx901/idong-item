package com.toltech.ou.service.system.ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toltech.ou.common.constant.UserConstants;
import com.toltech.ou.common.converter.Convert;
import com.toltech.ou.common.utils.ShiroUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.entity.system.Config;
import com.toltech.ou.mapper.system.ConfigMapper;
import com.toltech.ou.service.system.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 10:52
 * @Version 1.0
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper,Config> implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Config selectConfigById(Long configId) {
        Config config = new Config();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    @Override
    public String selectConfigByKey(String configKey) {
        Config config = new Config();
        config.setConfigKey(configKey);
        Config retConfig = configMapper.selectConfig(config);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
    }

    @Override
    public List<Config> selectConfigList(Config config) {
        return configMapper.selectConfigList(config);
    }

    @Override
    public int insertConfig(Config config) {
        config.setCreateBy(ShiroUtils.getLoginName());
        return configMapper.insertConfig(config);
    }

    @Override
    public int updateConfig(Config config) {
        config.setUpdateBy(ShiroUtils.getLoginName());
        return configMapper.updateConfig(config);
    }

    @Override
    public int deleteConfigByIds(String ids) {
        return configMapper.deleteConfigByIds(Convert.toStrArray(ids));
    }

    @Override
    public String checkConfigKeyUnique(Config config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        Config info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }
}
