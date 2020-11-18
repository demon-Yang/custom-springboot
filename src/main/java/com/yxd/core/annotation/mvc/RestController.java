package com.yxd.core.annotation.mvc;

import java.lang.annotation.*;

/**
 * @Description：自定义springmvc控制层的restful风格注解
 * @Date 2020/11/18 22:25
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RestController {
    String value() default "";
}
