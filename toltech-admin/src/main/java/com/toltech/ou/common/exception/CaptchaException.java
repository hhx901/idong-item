package com.toltech.ou.common.exception;

import com.toltech.ou.common.exception.user.UserException;

/**
 * 验证码错误异常类
 * @Author: ouyang
 * @Date: 2020/5/23 20:28
 * @Version 1.0
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
