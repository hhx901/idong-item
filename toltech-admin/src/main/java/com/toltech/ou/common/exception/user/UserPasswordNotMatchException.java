package com.toltech.ou.common.exception.user;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:56
 * @Version 1.0
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
