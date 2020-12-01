package com.yxd.core.annotation.ioc;

import java.lang.annotation.*;

/**
 * @Description：自动注入注解
 * @Date 2020/12/01 20:21
 * @Author YXD
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
