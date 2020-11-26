package com.yxd.core.proxy;

import com.yxd.core.aop.intercept.Interceptor;

/**
 * @Description：cglib动态代理类处理器
 * @Date 2020/11/26 21:00
 * @Author YXD
 * @Version 1.0
 */
public class CglibProxyProcessor extends ProxyProcessor{
    @Override
    public Object delegate(Object target, Interceptor interceptor) {
        return CglibProxy.delegate(target, interceptor);
    }
}
