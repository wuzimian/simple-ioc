package org.wzm.simple.ioc.aop.pointcut;

import java.lang.reflect.Method;

public interface PointCut {
    boolean classMatches(Object object);
    boolean methodMatches(Method method);
}
