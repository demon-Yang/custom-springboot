package com.yxd.core.proxy;

import com.yxd.core.aop.entity.MethodInvocation;
import com.yxd.core.aop.intercept.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description：jdk动态生成代理类
 * @Date 2020/11/26 22:07
 * @Author YXD
 * @Version 1.0
 */
public class JdkProxy implements InvocationHandler {
    private final Object target;
    private final Interceptor interceptor;

    public JdkProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object delegate(Object target, Interceptor interceptor) {
        JdkProxy jdkProxy = new JdkProxy(target, interceptor);
        Object object = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), jdkProxy);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}
