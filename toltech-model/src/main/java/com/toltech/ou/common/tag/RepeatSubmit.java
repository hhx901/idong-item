package com.toltech.ou.common.tag;

import java.lang.annotation.*;

/**
 * @Author: ouyang
 * @Date: 2020/5/23 21:06
 * @Version 1.0
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
}
