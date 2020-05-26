package com.toltech.ou.common.exception.file;

/**
 * @Author: ouyang
 * @Date: 2020/5/25 16:00
 * @Version 1.0
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
