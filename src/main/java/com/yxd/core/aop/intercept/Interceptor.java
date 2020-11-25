package com.yxd.core.aop.intercept;

import com.yxd.core.aop.entity.MethodInvocation;

/**
 * @Description：拦截器的抽象父类
 * @Date 2020/11/24 21:15
 * @Author YXD
 * @Version 1.0
 */
public abstract class Interceptor {
    private int order = -1;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean supports(Object bean) {
        //拦截器是否拦截该类，默认不拦截
        return false;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
