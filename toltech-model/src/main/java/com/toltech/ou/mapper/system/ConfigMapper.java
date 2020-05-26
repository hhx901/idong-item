package com.toltech.ou.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.system.Config;
import com.toltech.ou.entity.system.Dept;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 10:53
 * @Version 1.0
 */
public interface ConfigMapper extends BaseMapper<Config> {

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public Config selectConfig(Config config);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    public Config checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除参数配置
     *
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteConfigByIds(String[] configIds);
}
