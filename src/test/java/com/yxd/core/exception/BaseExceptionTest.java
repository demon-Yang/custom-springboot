package com.yxd.core.exception;

import org.junit.jupiter.api.Test;

/**
 * @Description：异常类测试
 * @Date 2020/11/17 20:28
 * @Author YXD
 * @Version 1.0
 */
class BaseExceptionTest {
    @Test
    void testException() throws BaseException{
        throw new BaseException("测试基础错误");
    }
}