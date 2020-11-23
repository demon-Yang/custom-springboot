package com.yxd.core.util;

import com.yxd.core.exception.BaseException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description：banner读取
 * @Date 2020/11/19 23:01
 * @Author YXD
 * @Version 1.0
 */
public class BannerUtil {
    private static final String BANNERURL = "banner.txt";

    public static void printBanner() {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(BANNERURL);
            Path path = Paths.get(url.toURI());
            Files.lines(path).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
