package com.yxd.core.factory;

import com.yxd.core.constant.SystemContants;
import com.yxd.core.proxy.ProxyProcessor;
import com.yxd.core.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：实例化注解的类
 * @Date 2020/11/24 20:03
 * @Author YXD
 * @Version 1.0
 */
public class BeanFactory {
    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>();

    public static void loadBean() {
        Set<Class<?>> controllerClasses = ClassFactory.CLASSES.get(SystemContants.REST_CONTROLLER);
        for (Class<?> controllerClass : controllerClasses) {
            Object instance = ReflectionUtil.newInstance(controllerClass);
            BEANS.put(controllerClass.getName(), instance);
        }

        Set<Class<?>> serviceClasses = ClassFactory.CLASSES.get(SystemContants.SERVICE);
        for (Class<?> serviceClass : serviceClasses) {
            Object instance = ReflectionUtil.newInstance(serviceClass);
            BEANS.put(serviceClass.getName(), instance);
        }
    }

    /**
     * 动态代理有被拦截的类
     */
    public static void beanAfterProcessor() {
        BEANS.replaceAll((beanName, beanInstance) -> {
            ProxyProcessor proxyProcessor = ProxyFactory.chooseStrategy(beanInstance.getClass());
            return proxyProcessor.delegateBean(beanInstance);
        });
    }
}
