package com.toltech.ou.common.exception.file;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 16:00
 * @Version 1.0
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}
