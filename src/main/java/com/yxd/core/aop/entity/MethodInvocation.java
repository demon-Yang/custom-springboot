package com.yxd.core.aop.entity;

import com.yxd.core.util.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * @Description：反射方法调用
 * @Date 2020/11/24 21:21
 * @Author YXD
 * @Version 1.0
 */
public class MethodInvocation extends JoinPoint{

    public MethodInvocation(Object targetObject, Method targetMethod, Object[] args) {
        super(targetObject, targetMethod, args);
    }

    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
