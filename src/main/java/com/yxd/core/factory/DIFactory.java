package com.yxd.core.factory;

import com.yxd.core.annotation.ioc.Autowired;
import com.yxd.core.annotation.ioc.Qualifier;
import com.yxd.core.exception.BaseException;
import com.yxd.core.proxy.ProxyProcessor;
import com.yxd.core.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @Description：依赖注入实例
 * @Date 2020/12/01 20:25
 * @Author YXD
 * @Version 1.0
 */
public class DIFactory {
    public static void initBean(String[] packageNames) {
        Map<String, Object> beans = BeanFactory.BEANS;
        beans.values().forEach(object -> {
            Field[] fields = object.getClass().getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                Object autowiredObject = obtainAutowiredObject(packageNames, field);
                ProxyProcessor proxyProcessor = ProxyFactory.chooseStrategy(autowiredObject.getClass());
                ReflectionUtil.setField(object, field, proxyProcessor.delegateBean(autowiredObject));
            });
        });
    }

    /**
     * 获取自动注入的实例
     * @param packageNames
     * @param field
     * @return
     */
    public static Object obtainAutowiredObject(String[] packageNames, Field field) {
        Class<?> fieldClass = field.getType();
        String beanName = fieldClass.getName();
        if (field.isAnnotationPresent(Autowired.class)) {
            Set<Class<?>> subClasses = ReflectionUtil.scanSubClass(packageNames, (Class<Object>) fieldClass);
            if (subClasses.size() == 0) {
                throw new BaseException(fieldClass.getName() + "is interface and do not have implemented class exception");
            } else if (subClasses.size() == 1) {
                beanName =  subClasses.iterator().next().getName();
            } else {
                Qualifier qualifier = subClasses.getClass().getDeclaredAnnotation(Qualifier.class);
                beanName =  qualifier == null ? beanName : qualifier.value();
            }
        }
        Object instance = BeanFactory.BEANS.get(beanName);
        if (instance == null) {
            throw new BaseException(fieldClass.getName() + "can not determine target bean");
        }
        return instance;
    }
}
