package com.yxd.core.annotation.boot;

import java.lang.annotation.*;

/**
 * @Description：自定义springboot启动类注解
 * @Date 2020/11/18 21:27
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SpringBootApplication {
}
