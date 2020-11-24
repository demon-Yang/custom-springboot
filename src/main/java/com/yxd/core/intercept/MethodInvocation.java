package com.yxd.core.intercept;

import com.yxd.core.util.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * @Description：反射方法调用
 * @Date 2020/11/24 21:21
 * @Author YXD
 * @Version 1.0
 */
public class MethodInvocation {
    private final Object targetObject;
    private final Method targetMethod;
    private final Object[] args;

    public MethodInvocation(Object targetObject, Method targetMethod, Object[] args) {
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

    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
