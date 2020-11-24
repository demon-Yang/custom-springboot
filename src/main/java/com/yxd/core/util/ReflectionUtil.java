package com.yxd.core.util;

import com.yxd.core.exception.BaseException;
import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     *
     * @param packageNames
     * @param annotation
     */
    public static Set<Class<?>> scanAnnotationClass(String[] packageNames, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        return annotatedClass;
    }

    /**
     * 扫描包下面的所有该类的子类
     * @param packageNames
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public static <T> Set<Class<? extends T>>  scanSubClass(String[] packageNames, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<? extends T>> subTypesOf = reflections.getSubTypesOf(interfaceClass);
        return subTypesOf;
    }

    /**
     * 实例化类
     *
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }

    }

    /**
     * 执行目标方法
     *
     * @param targetObject
     * @param targetMethod
     * @param args
     * @return
     */
    public static Object executeTargetMethod(Object targetObject, Method targetMethod, Object[] args) {
        try {
            return targetMethod.invoke(targetObject, args);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }

}