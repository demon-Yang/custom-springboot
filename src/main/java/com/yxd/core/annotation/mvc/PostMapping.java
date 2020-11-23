package com.yxd.core.annotation.mvc;

import java.lang.annotation.*;

/**
 * @Description：http的POST请求注解
 * @Date 2020/11/23 22:23
 * @Author YXD
 * @Version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {
    String value() default "";
}
