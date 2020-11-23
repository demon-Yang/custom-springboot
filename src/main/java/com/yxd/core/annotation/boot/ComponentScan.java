package com.yxd.core.annotation.boot;

import java.lang.annotation.*;

/**
 * @Description：扫描value的所有注解
 * @Date 2020/11/23 21:04
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {
    String[] value() default {};
}
