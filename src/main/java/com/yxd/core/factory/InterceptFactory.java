package com.yxd.core.factory;

import com.yxd.core.intercept.Interceptor;
import com.yxd.core.util.ReflectionUtil;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description：设置拦截器
 * @Date 2020/11/24 21:09
 * @Author YXD
 * @Version 1.0
 */
public class InterceptFactory {
    private static final List INTERCEPTOR_LIST = new CopyOnWriteArrayList();

    public static void loadIntercept(String[] packageNames) {
        // 获取指定包中实现了 Interceptor 接口的类
        Set<Class<? extends Interceptor>> interceptorClasses = ReflectionUtil.scanSubClass(packageNames, Interceptor.class);

    }
}
