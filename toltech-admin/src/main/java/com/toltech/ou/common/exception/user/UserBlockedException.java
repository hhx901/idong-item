package com.toltech.ou.common.exception.user;

/**
 * @Author: ouyang
 * @Date: 2020/5/23 20:32
 * @Version 1.0
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException()
    {
        super("user.blocked", null);
    }
}
