package com.toltech.ou.controller.monitor;

import com.toltech.ou.controller.core.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: ouyang
 * @Date: 2020/5/26 13:50
 * @Version 1.0
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController {
    private String prefix = "/druid";

    @RequiresPermissions("monitor:data:view")
    @GetMapping()
    public String index() {
        return redirect(prefix + "/index");
    }
}
