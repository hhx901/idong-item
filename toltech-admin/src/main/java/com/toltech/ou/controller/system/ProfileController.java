package com.toltech.ou.controller.system;

import com.toltech.ou.common.assist.AjaxResult;
import com.toltech.ou.common.config.IdongConfig;
import com.toltech.ou.common.enums.BusinessType;
import com.toltech.ou.common.shiro.service.PasswordService;
import com.toltech.ou.common.tag.Log;
import com.toltech.ou.common.utils.FileUtils;
import com.toltech.ou.common.utils.StringUtils;
import com.toltech.ou.controller.core.BaseController;
import com.toltech.ou.entity.system.User;
import com.toltech.ou.service.system.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 15:50
 * @Version 1.0
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        User user = getSysUser();
        if (passwordService.matches(user, password)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword)
    {
        User user = getSysUser();
        if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword)) {
            user.setPassword(newPassword);
            if (userService.resetUserPwd(user) > 0) {
                setSysUser(userService.selectUserById(user.getUserId()));
                return success();
            }
            return error();
        } else {
            return error("修改密码失败，旧密码错误");
        }
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(User user) {
        User currentUser = getSysUser();
        currentUser.setUserName(user.getUserName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        if (userService.updateUserInfo(currentUser) > 0) {
            setSysUser(userService.selectUserById(currentUser.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
        User currentUser = getSysUser();
        try {
            if (!file.isEmpty()) {
                String avatar = FileUtils.upload(IdongConfig.getAvatarPath(), file);
                currentUser.setAvatar(avatar);
                if (userService.updateUserInfo(currentUser) > 0) {
                    setSysUser(userService.selectUserById(currentUser.getUserId()));
                    return success();
                }
            }
            return error();
        } catch (Exception e) {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
