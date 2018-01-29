package org.wzm.simple.ioc.aop.pointcut;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultPointCut implements PointCut{
    private String classCut;
    private String methodCut;

    public DefaultPointCut(String classCut, String methodCut) {
        this.classCut = classCut;
        this.methodCut = methodCut;
    }

    @Override
    public boolean classMatches(Object object) {
        Class<?> clazz = object.getClass();
        String clazzName = clazz.getName();
        Pattern pattern = Pattern.compile(classCut);
        Matcher matcher = pattern.matcher(clazzName);
        return matcher.find();
    }

    @Override
    public boolean methodMatches(Method method) {
        String methodName = method.getName();
        Pattern pattern = Pattern.compile(methodCut);
        Matcher matcher = pattern.matcher(methodName);
        return matcher.find();
    }
}
