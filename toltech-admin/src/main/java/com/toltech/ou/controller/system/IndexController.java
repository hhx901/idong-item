package com.toltech.ou.controller.system;

import com.toltech.ou.common.config.IdongConfig;
import com.toltech.ou.controller.core.BaseController;
import com.toltech.ou.entity.system.Menu;
import com.toltech.ou.entity.system.User;
import com.toltech.ou.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: ouyang
 * @Date: 2020/5/22 15:59
 * @Version 1.0
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private MenuService menuService;

    @Autowired
    private IdongConfig idongConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", idongConfig.getCopyrightYear());
        mmap.put("demoEnabled", idongConfig.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap)
    {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", idongConfig.getVersion());
        return "main";
    }
}
