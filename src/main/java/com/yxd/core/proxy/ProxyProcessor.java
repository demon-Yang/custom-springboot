package com.yxd.core.proxy;

import com.yxd.core.aop.intercept.Interceptor;
import com.yxd.core.factory.InterceptFactory;

/**
 * @Description：代理类处理器
 * @Date 2020/11/26 20:21
 * @Author YXD
 * @Version 1.0
 */
public abstract class ProxyProcessor {

    public Object delegateBean(Object bean) {
        Object wrapperBean = bean;
        //链式包装目标类
        for (Interceptor interceptor : InterceptFactory.getInterceptors()) {
            if (interceptor.supports(bean)) {
                wrapperBean = delegate(wrapperBean, interceptor);
            }
        }
        return wrapperBean;
    }

    public abstract Object delegate(Object target, Interceptor interceptor);
}
