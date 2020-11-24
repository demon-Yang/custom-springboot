package com.yxd.core.intercept;

/**
 * @Description：拦截器的抽象父类
 * @Date 2020/11/24 21:15
 * @Author YXD
 * @Version 1.0
 */
public abstract class Interceptor {
    private int order = 0;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
