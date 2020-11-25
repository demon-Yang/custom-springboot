package com.yxd.core.annotation.aop;

import java.lang.annotation.*;

/**
 * @Description：切面的执行顺序
 * @Date 2020/11/25 21:11
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    int value() default -1;
}
