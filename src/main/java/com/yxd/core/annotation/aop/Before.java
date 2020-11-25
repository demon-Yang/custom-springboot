package com.yxd.core.annotation.aop;

import java.lang.annotation.*;

/**
 * @Description：切面前置通知
 * @Date 2020/11/25 20:37
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Before {
}
