package com.toltech.ou.common.exception.file;

import com.toltech.ou.common.exception.BaseException;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 16:01
 * @Version 1.0
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }
}
