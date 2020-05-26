package com.toltech.ou.common.exception.user;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:54
 * @Version 1.0
 */
public class UserPasswordRetryLimitExceedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount)
    {
        super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}

