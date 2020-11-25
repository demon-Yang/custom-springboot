package com.yxd.core.annotation.mvc;

import java.lang.annotation.*;

/**
 * @Description：springmvc控制层注解
 * @Date 2020/11/18 22:25
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    String value() default "";
}
