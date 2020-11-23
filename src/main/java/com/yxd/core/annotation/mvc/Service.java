package com.yxd.core.annotation.mvc;

import java.lang.annotation.*;

/**
 * @Description：springmvc服务层注解
 * @Date 2020/11/23 21:52
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
