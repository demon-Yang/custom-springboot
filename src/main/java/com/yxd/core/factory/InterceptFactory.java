package com.yxd.core.factory;

import com.yxd.core.annotation.aop.Aspect;
import com.yxd.core.annotation.aop.Order;
import com.yxd.core.aop.intercept.AspectInterceptor;
import com.yxd.core.aop.intercept.Interceptor;
import com.yxd.core.constant.SystemContants;
import com.yxd.core.util.ReflectionUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @Description：设置拦截器
 * @Date 2020/11/24 21:09
 * @Author YXD
 * @Version 1.0
 */
public class InterceptFactory {
    private static final List INTERCEPTOR_LIST = new CopyOnWriteArrayList();

    public static void loadIntercept(String[] packageNames) {
        // 获取实现了 Interceptor 接口的类
        Set<Class<? extends Interceptor>> interceptorClasses = ReflectionUtil.scanSubClass(packageNames, Interceptor.class);
        // 获取被 @Aspect 标记的类
        Set<Class<?>> aspects = ClassFactory.CLASSES.get(SystemContants.ASPECT);
        // 添加到容器中
        interceptorClasses.forEach(interceptorClass -> {
            INTERCEPTOR_LIST.add(ReflectionUtil.newInstance(interceptorClass));
        });
        aspects.forEach(aClass -> {
            Object object = ReflectionUtil.newInstance(aClass);
            Interceptor interceptor = new AspectInterceptor(object);
            if (aClass.isAnnotationPresent(Order.class)) {
                Order order = aClass.getAnnotation(Order.class);
                interceptor.setOrder(order.value());
            }
            INTERCEPTOR_LIST.add(interceptor);
        });
        //排序
        INTERCEPTOR_LIST.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    /**
     * 获取所有拦截类
     * @return
     */
    public static List<Interceptor> getInterceptors() {
        return INTERCEPTOR_LIST;
    }
}
