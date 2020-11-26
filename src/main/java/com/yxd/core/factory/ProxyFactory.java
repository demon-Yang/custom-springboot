package com.yxd.core.factory;

import com.yxd.core.proxy.CglibProxyProcessor;
import com.yxd.core.proxy.JdkProxyProcessor;
import com.yxd.core.proxy.ProxyProcessor;

/**
 * @Description：代理类工厂
 * @Date 2020/11/26 20:56
 * @Author YXD
 * @Version 1.0
 */
public class ProxyFactory {
    /**
     * 实现了接口就返回jdk动态代理的对象，否则返回cglib动态代理的对象
     * @param beanClass
     * @return
     */
    public static ProxyProcessor chooseStrategy(Class<?> beanClass) {
        if (beanClass.isInstance(beanClass) || beanClass.getInterfaces().length > 0) {
            return new JdkProxyProcessor();
        } else {
            return new CglibProxyProcessor();
        }
    }
}
