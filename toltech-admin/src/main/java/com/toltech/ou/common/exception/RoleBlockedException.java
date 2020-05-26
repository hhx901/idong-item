package com.toltech.ou.common.exception;

import com.toltech.ou.common.exception.user.UserException;

/**
 * @Author: ouyang
 * @Date: 2020/5/23 20:35
 * @Version 1.0
 */
public class RoleBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public RoleBlockedException(){
        super("user.blocked", null);
    }
}