package com.toltech.ou.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.toltech.ou.entity.log.SysLog;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 14:50
 * @Version 1.0
 */
public interface SysLogService extends IService<SysLog>{
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
     * @return
     */
    public int deleteLogininforByIds(String ids);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();
}
