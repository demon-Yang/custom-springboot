package com.yxd.demo.controller;

import com.yxd.core.annotation.mvc.GetMapping;
import com.yxd.core.annotation.mvc.PostMapping;
import com.yxd.core.annotation.mvc.RestController;

/**
 * @Description：demo控制层
 * @Date 2020/11/23 21:40
 * @Author YXD
 * @Version 1.0
 */
@RestController("/demo")
public class DemoController {

    @GetMapping("/testGet")
    public void testGetMapping() {}

    @PostMapping("/testPost")
    public void testPostMapping() {}
}
