package com.toltech.ou.controller.log;

import com.toltech.ou.common.assist.AjaxResult;
import com.toltech.ou.common.enums.BusinessType;
import com.toltech.ou.common.page.TableDataInfo;
import com.toltech.ou.common.poi.ExcelUtil;
import com.toltech.ou.common.shiro.service.PasswordService;
import com.toltech.ou.common.tag.Log;
import com.toltech.ou.controller.core.BaseController;
import com.toltech.ou.entity.log.SysLog;
import com.toltech.ou.service.log.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 15:06
 * @Version 1.0
 */

@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogController extends BaseController {
    private String prefix = "monitor/logininfor";

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private PasswordService passwordService;

    @RequiresPermissions("monitor:logininfor:view")
    @GetMapping()
    public String logininfor() {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("monitor:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLog logininfor) {
        startPage();
        List<SysLog> list = sysLogService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登陆日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:logininfor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysLog logininfor) {
        List<SysLog> list = sysLogService.selectLogininforList(logininfor);
        ExcelUtil<SysLog> util = new ExcelUtil<SysLog>(SysLog.class);
        return util.exportExcel(list, "登陆日志");
    }

    @RequiresPermissions("monitor:logininfor:remove")
    @Log(title = "登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysLogService.deleteLogininforByIds(ids));
    }

    @RequiresPermissions("monitor:logininfor:remove")
    @Log(title = "登陆日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        sysLogService.cleanLogininfor();
        return success();
    }

    @RequiresPermissions("monitor:logininfor:unlock")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping("/unlock")
    @ResponseBody
    public AjaxResult unlock(String loginName) {
        passwordService.unlock(loginName);
        return success();
    }
}
