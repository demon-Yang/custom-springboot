package com.yxd.core.annotation.ioc;

import java.lang.annotation.*;

/**
 * @Description：依赖别名注解
 * @Date 2020/12/01 20:23
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {
    String value() default "";
}
