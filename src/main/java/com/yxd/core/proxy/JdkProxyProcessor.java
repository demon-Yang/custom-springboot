package com.yxd.core.proxy;

import com.yxd.core.aop.intercept.Interceptor;

/**
 * @Description：jdk动态代理类处理器
 * @Date 2020/11/26 20:59
 * @Author YXD
 * @Version 1.0
 */
public class JdkProxyProcessor extends ProxyProcessor {

    @Override
    public Object delegate(Object target, Interceptor interceptor) {
       return JdkProxy.delegate(target, interceptor);
    }
}
