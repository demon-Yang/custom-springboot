package com.yxd.core.factory;

import com.yxd.core.annotation.mvc.GetMapping;
import com.yxd.core.annotation.mvc.PostMapping;
import com.yxd.core.annotation.mvc.RestController;
import com.yxd.core.constant.SystemContants;
import com.yxd.core.exception.BaseException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：mvc模式的路径映射
 * @Date 2020/11/23 22:28
 * @Author YXD
 * @Version 1.0
 */
public class RouteFactory {
    private static final Map<String, Method> GET_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Method> POST_MAP = new ConcurrentHashMap<>();

    public static void loadRoute() {
        Set<Class<?>> controllerClasses = ClassFactory.CLASSES.get(SystemContants.REST_CONTROLLER);
        for (Class<?> controllerClass : controllerClasses) {
            RestController restController = controllerClass.getAnnotation(RestController.class);
            String baseUrl = restController.value();
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
                    if (Objects.nonNull(getMapping)) {
                        String url = baseUrl + getMapping.value();
                        if (GET_MAP.containsKey(url)) {
                            throw new BaseException(String.format("duplicate http.get url: %s", url));
                        }
                        GET_MAP.put(url, method);
                    }
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    PostMapping postMapping = method.getAnnotation(PostMapping.class);
                    String url = baseUrl + postMapping.value();
                    if (POST_MAP.containsKey(url)) {
                        throw new BaseException(String.format("duplicate http.post url: %s", url));
                    }
                    POST_MAP.put(baseUrl + postMapping.value(), method);
                }
            }
        }
    }
}
