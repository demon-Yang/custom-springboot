package com.yxd.core.aop.entity;

import java.lang.reflect.Method;

/**
 * @Description：切面通知类
 * @Date 2020/11/25 21:58
 * @Author YXD
 * @Version 1.0
 */
public class JoinPoint {
    public final Object targetObject;
    public final Method targetMethod;
    public final Object[] args;

    public JoinPoint(Object targetObject, Method targetMethod, Object[] args) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }
}
