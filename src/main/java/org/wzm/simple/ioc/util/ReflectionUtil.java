package org.wzm.simple.ioc.util;

public class ReflectionUtil {
    public static Object getInstance(String className) {
        Object instance = null;
        try {
            instance = getClass(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Class<?> getClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Class<?>[] getImplementedInterface(Object object) {
        Class<?> clazz = object.getClass();
        return clazz.getInterfaces();
    }
}
