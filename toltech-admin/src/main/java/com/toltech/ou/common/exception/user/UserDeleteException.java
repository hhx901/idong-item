package com.toltech.ou.common.exception.user;

/**
 * @Author: ouyang
 * @Date: 2020/5/23 20:31
 * @Version 1.0
 */
public class UserDeleteException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super("user.password.delete", null);
    }
}
