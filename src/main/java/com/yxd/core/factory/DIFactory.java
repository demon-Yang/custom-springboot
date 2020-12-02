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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：依赖注入实例
 * @Date 2020/12/01 20:25
 * @Author YXD
 * @Version 1.0
 */
public class DIFactory {

    private final String[] packageNames;

    //二级缓存（解决循环依赖问题）
    private static final Map<String, Object> SINGLETON_OBJECTS = new ConcurrentHashMap<>();

    public DIFactory(String[] packageNames) {
        this.packageNames = packageNames;
    }

    /**
     * 依赖注入
     */
    public void inject() {
        Map<String, Object> beans = BeanFactory.BEANS;
        beans.values().forEach(object -> {
            initBean(object);
        });
    }

    public void initBean(Object beanInstance) {
        Field[] fields = beanInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            Object beanFieldInstance = obtainAutowiredObject(packageNames, field);
            // 解决循环依赖问题
            beanFieldInstance = resolveCircularDependency(beanInstance, beanFieldInstance);
            ProxyProcessor proxyProcessor = ProxyFactory.chooseStrategy(beanFieldInstance.getClass());
            ReflectionUtil.setField(beanInstance, field, proxyProcessor.delegateBean(beanFieldInstance));
        });
    }

    /**
     * 获取自动注入的实例
     *
     * @param packageNames
     * @param field
     * @return
     */
    public Object obtainAutowiredObject(String[] packageNames, Field field) {
        Class<?> fieldClass = field.getType();
        String beanName = fieldClass.getName();
        if (field.isAnnotationPresent(Autowired.class)) {
            Set<Class<?>> subClasses = ReflectionUtil.scanSubClass(packageNames, (Class<Object>) fieldClass);
            if (subClasses.size() == 0) {
                throw new BaseException(fieldClass.getName() + "is interface and do not have implemented class exception");
            } else if (subClasses.size() == 1) {
                beanName = subClasses.iterator().next().getName();
            } else {
                Qualifier qualifier = subClasses.getClass().getDeclaredAnnotation(Qualifier.class);
                beanName = qualifier == null ? beanName : qualifier.value();
            }
        }
        Object instance = BeanFactory.BEANS.get(beanName);
        if (instance == null) {
            throw new BaseException(fieldClass.getName() + "can not determine target bean");
        }
        return instance;
    }

    /**
     * 二级缓存解决循环依赖问题
     */
    private Object resolveCircularDependency(Object beanInstance, Object beanFieldInstance) {
        String beanFieldName = beanFieldInstance.getClass().getName();
        if (SINGLETON_OBJECTS.containsKey(beanFieldName)) {
            beanFieldInstance = SINGLETON_OBJECTS.get(beanFieldName);
        } else {
            SINGLETON_OBJECTS.put(beanFieldName, beanFieldInstance);
            initBean(beanInstance);
        }
        return beanFieldInstance;
    }}
