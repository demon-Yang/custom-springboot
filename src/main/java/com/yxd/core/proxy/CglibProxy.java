package com.yxd.core.proxy;

import com.yxd.core.aop.entity.MethodInvocation;
import com.yxd.core.aop.intercept.Interceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description：cglib动态生成代理类
 * @Date 2020/11/26 22:13
 * @Author YXD
 * @Version 1.0
 */
public class CglibProxy implements MethodInterceptor {
    private final Object target;
    private final Interceptor interceptor;

    public CglibProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object delegate(Object target, Interceptor interceptor) {
        Class<?> rootClass = target.getClass();
        Class<?> proxySuperClass = rootClass;
        // cglib 多级代理处理
        if (target.getClass().getName().contains("$$")) {
            proxySuperClass = rootClass.getSuperclass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new CglibProxy(target, interceptor));
        Object object = enhancer.create();
        return object;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}
