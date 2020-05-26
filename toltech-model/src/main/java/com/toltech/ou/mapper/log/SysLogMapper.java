package com.toltech.ou.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toltech.ou.entity.log.SysLog;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 14:48
 * @Version 1.0
 */
public interface SysLogMapper extends BaseMapper<SysLog>{
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLog logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLog> selectLogininforList(SysLog logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteLogininforByIds(String[] ids);

    /**
     * 清空系统登录日志
     * @return 结果
     */
    public int cleanLogininfor();
}
