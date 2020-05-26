package com.toltech.ou.common.tag;

import java.lang.annotation.*;

/**
 * @Author: ouyang
 * @Date: 2020/5/21 13:48
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";
}
