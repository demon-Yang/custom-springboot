package com.yxd.core;

import com.yxd.core.annotation.boot.ComponentScan;
import com.yxd.core.factory.BeanFactory;
import com.yxd.core.factory.ClassFactory;
import com.yxd.core.factory.InterceptFactory;
import com.yxd.core.factory.RouteFactory;
import com.yxd.core.util.BannerUtil;

import java.util.Objects;

/**
 * @Description：启动类
 * @Date 2020/11/23 20:24
 * @Author YXD
 * @Version 1.0
 */
public class ApplicationContext {
    public static void run(Class<?> applicationClass) {
        //打印banner
        BannerUtil.printBanner();
        //获取扫描包，加载类
        String[] packageNames = obtainPackageNames(applicationClass);
        ClassFactory.loadClass(packageNames);
        //获取mvc模式的路径映射
        RouteFactory.loadRoute();
        //实例化注解的类
        BeanFactory.loadBean();
        //设置拦截器和AOP
        InterceptFactory.loadIntercept(packageNames);
        //有被拦截的，动态生成类
        BeanFactory.beanAfterProcessor();
    }

    /**
     * 获取需要的扫描包
     * @param applicationClass
     * @return
     */
    private static String[] obtainPackageNames(Class<?> applicationClass) {
        ComponentScan componentScan = applicationClass.getAnnotation(ComponentScan.class);
        return !Objects.isNull(componentScan) ? componentScan.value()
                : new String[]{applicationClass.getPackage().getName()};
    }
}
