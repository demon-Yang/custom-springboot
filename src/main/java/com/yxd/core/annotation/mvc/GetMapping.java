package com.yxd.core.annotation.mvc;

import java.lang.annotation.*;

/**
 * @Description：http的GET请求注解
 * @Date 2020/11/23 22:20
 * @Author YXD
 * @Version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {
    String value() default "";
}
