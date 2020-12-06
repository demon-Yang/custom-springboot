package com.yxd.demo.controller;

import com.yxd.core.annotation.ioc.Autowired;
import com.yxd.core.annotation.mvc.GetMapping;
import com.yxd.core.annotation.mvc.PostMapping;
import com.yxd.core.annotation.mvc.RestController;
import com.yxd.demo.serivce.DemoService;

/**
 * @Description：demo控制层
 * @Date 2020/11/23 21:40
 * @Author YXD
 * @Version 1.0
 */
@RestController("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/testGet")
    public String testGetMapping() {
        return demoService.testGetMapping();
    }

    @PostMapping("/testPost")
    public String testPostMapping() {
        return demoService.testPostMapping();
    }
}
