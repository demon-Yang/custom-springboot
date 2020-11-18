package com.yxd.core.util;

import com.yxd.core.annotation.boot.SpringBootApplication;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * @Description：
 * @Date 2020/11/18 21:21
 * @Author YXD
 * @Version 1.0
 */
class ReflectionUtilTest {

    @Test
    void scanAnnotationClass() {
        Set<Class<?>> classes = ReflectionUtil.scanAnnotationClass(new String[]{"com.yxd.demo"}, SpringBootApplication.class);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            LogbackUtil.info("--扫描到的的类：{}--", next.getName());
        }

    }
}