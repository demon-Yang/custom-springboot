package com.yxd.core.aop.intercept;

import com.yxd.core.annotation.aop.After;
import com.yxd.core.annotation.aop.Before;
import com.yxd.core.annotation.aop.Pointcut;
import com.yxd.core.aop.entity.JoinPoint;
import com.yxd.core.aop.entity.MethodInvocation;
import com.yxd.core.util.AspectPatternUtil;
import com.yxd.core.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description：切面拦截器
 * @Date 2020/11/25 21:18
 * @Author YXD
 * @Version 1.0
 */
public class AspectInterceptor extends Interceptor {

    private final Object adviceBean;
    private final Set<String> executionUrls = new CopyOnWriteArraySet<>();
    private final List<Method> beforeMethods = new CopyOnWriteArrayList<>();
    private final List<Method> afterMethods = new CopyOnWriteArrayList<>();

    public AspectInterceptor(Object adviceBean) {
        this.adviceBean = adviceBean;
        init();
    }

    private void init() {
        Method[] methods = adviceBean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Pointcut.class)) {
                Pointcut pointcut = method.getAnnotation(Pointcut.class);
                executionUrls.add(pointcut.value());
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
    }

    public boolean supports(Object bean) {
        return executionUrls.stream().anyMatch(url ->
                AspectPatternUtil.simpleMatch(url, bean.getClass().getName()))
                && (!beforeMethods.isEmpty() || !afterMethods.isEmpty());
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = methodInvocation;
        //前置通知
        beforeMethods.forEach(method ->
                ReflectionUtil.executeTargetMethodNotResult(
                        adviceBean,
                        method,
                        joinPoint));
        //正式执行
        Object result = methodInvocation.proceed();
        //后置通知
        afterMethods.forEach(method ->
                ReflectionUtil.executeTargetMethodNotResult(
                        adviceBean,
                        method,
                        result, joinPoint));
        return result;
    }
}
