package com.yxd.core.util;

import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @Description：反射类的使用
 * @Date 2020/11/18 20:44
 * @Author YXD
 * @Version 1.0
 */
public class ReflectionUtil {
    /**
     * 扫描包下面的所有该注解的class类
     * @param packageNames
     * @param annotation
     */
    public static Set<Class<?>> scanAnnotationClass(String[] packageNames, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        return annotatedClass;
    }
}
