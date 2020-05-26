package com.toltech.ou.common.tag;

import com.toltech.ou.common.enums.BusinessType;
import com.toltech.ou.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @Author: ouyang
 * @Date: 2019/7/2 15:00
 * @Version 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
