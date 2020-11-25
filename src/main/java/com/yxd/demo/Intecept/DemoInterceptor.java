package com.yxd.demo.Intecept;

import com.yxd.core.aop.entity.MethodInvocation;
import com.yxd.core.aop.intercept.Interceptor;
import com.yxd.core.util.LogbackUtil;

/**
 * @Description：拦截器
 * @Date 2020/11/25 22:58
 * @Author YXD
 * @Version 1.0
 */
public class DemoInterceptor extends Interceptor {
    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        LogbackUtil.info("拦截类: {}", methodInvocation.getTargetObject());
        LogbackUtil.info("拦截方法: {}", methodInvocation.getTargetMethod());
        LogbackUtil.info("拦截参数: {}", methodInvocation.getArgs());
        Object result = methodInvocation.proceed();
        return result;
    }
}
