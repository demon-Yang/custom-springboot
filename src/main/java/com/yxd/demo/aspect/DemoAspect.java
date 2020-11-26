package com.yxd.demo.aspect;

import com.yxd.core.annotation.aop.*;
import com.yxd.core.aop.entity.JoinPoint;
import com.yxd.core.util.LogbackUtil;

/**
 * @Description：切面
 * @Date 2020/11/25 23:06
 * @Author YXD
 * @Version 1.0
 */
@Aspect
@Order(0)
public class DemoAspect {

    @Pointcut("com.yxd.demo.*.*Controller*")
    void controllerPointcut() {
    }

    @Pointcut("com.yxd.demo.*.*Service*")
    void servicePointcut() {
    }

    @Before
    void before(JoinPoint joinPoint) {
        LogbackUtil.info("拦截类: {}", joinPoint.getTargetObject());
        LogbackUtil.info("拦截方法: {}", joinPoint.getTargetMethod());
        LogbackUtil.info("拦截参数: {}", joinPoint.getArgs());
    }

    @After
    void after(Object result, JoinPoint joinPoint) {
        LogbackUtil.info("返回结果: {}", result);
        LogbackUtil.info("拦截类: {}", joinPoint.getTargetObject());
        LogbackUtil.info("拦截方法: {}", joinPoint.getTargetMethod());
        LogbackUtil.info("拦截参数: {}", joinPoint.getArgs());
    }
}
