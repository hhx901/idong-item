package com.toltech.ou.common.exception.user;

/**
 * 用户不存在异常类
 * @Author: ouyang
 * @Date: 2020/5/23 20:28
 * @Version 1.0
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
