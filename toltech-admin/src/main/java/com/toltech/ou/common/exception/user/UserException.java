package com.toltech.ou.common.exception.user;

import com.toltech.ou.common.exception.BaseException;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 10:55
 * @Version 1.0
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }

}