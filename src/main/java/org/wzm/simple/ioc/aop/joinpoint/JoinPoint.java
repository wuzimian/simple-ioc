package org.wzm.simple.ioc.aop.joinpoint;

import org.wzm.simple.ioc.aop.Chain;

import java.lang.reflect.Method;

public interface JoinPoint {
    Object getTargetBean();
    Method getTargetMethod();
    Object [] getArgs();
}
