package com.yxd.core.factory;

import com.yxd.core.annotation.aop.Aspect;
import com.yxd.core.annotation.mvc.RestController;
import com.yxd.core.annotation.mvc.Service;
import com.yxd.core.constant.SystemContants;
import com.yxd.core.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：初始化扫描注解的类
 * @Date 2020/11/23 21:24
 * @Author YXD
 * @Version 1.0
 */
public class ClassFactory {
    public static final Map<String, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();

    public static void loadClass(String[] packageNames) {
        Set<Class<?>> restControllers = ReflectionUtil.scanAnnotationClass(packageNames, RestController.class);
        Set<Class<?>> services = ReflectionUtil.scanAnnotationClass(packageNames, Service.class);
        Set<Class<?>> aspects = ReflectionUtil.scanAnnotationClass(packageNames, Aspect.class);
        CLASSES.put(SystemContants.REST_CONTROLLER, restControllers);
        CLASSES.put(SystemContants.SERVICE, services);
        CLASSES.put(SystemContants.ASPECT, aspects);
    }
}
