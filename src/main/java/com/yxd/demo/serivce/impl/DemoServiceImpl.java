package com.yxd.demo.serivce.impl;

import com.yxd.core.annotation.mvc.Service;
import com.yxd.core.util.LogbackUtil;
import com.yxd.demo.serivce.DemoService;

/**
 * @Description：demo服务实现层
 * @Date 2020/11/23 21:57
 * @Author YXD
 * @Version 1.0
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String testGetMapping() {
        LogbackUtil.info("---调用testGetMapping方法---");
        return "调用testGetMapping方法";
    }

    @Override
    public String testPostMapping() {
        LogbackUtil.info("---调用testPostMapping方法---");
        return "调用testPostMapping方法";
    }
}
