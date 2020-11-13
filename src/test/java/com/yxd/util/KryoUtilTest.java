package com.yxd.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Description：kryo序列化测试
 * @Date 2020/11/13 16:20
 * @Author YXD
 * @Version 1.0
 */
class KryoUtilTest {

    @BeforeEach
    void setUp() {
        LogbackUtil.info("--- kryo序列化开始 ---");
    }

    @AfterEach
    void tearDown() {
        LogbackUtil.info("--- kryo序列化结束 ---");
    }

    @Test
    void serialize() {
        String testString = "hello world!";
        byte[] bytes = KryoUtil.serialize(testString);
        LogbackUtil.info("--- kryo序列化结果 {} ---", bytes);
    }

    @Test
    void deserialize() {
        byte[] bytes = new byte[]{3, 1, 104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, -95};
        Object deserialize = KryoUtil.deserialize(bytes);
        LogbackUtil.info("--- kryo反序列化结果 {} ---", deserialize);
    }
}