package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.AspectInstanceFactory;
import org.wzm.simple.ioc.aop.joinpoint.JoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAdvice implements Advice {
    private AspectInstanceFactory aspectInstanceFactory;
    private Method adviceMethod;

    public AbstractAdvice(AspectInstanceFactory aspectInstanceFactory, Method adviceMethod) {
        this.aspectInstanceFactory = aspectInstanceFactory;
        this.adviceMethod = adviceMethod;
    }

    protected Object invokeAdviceMethod(JoinPoint joinPoint){
        try {
            return adviceMethod.invoke(getAspectInstance(),joinPoint);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getAspectInstance(){
        return aspectInstanceFactory.getAspectInstance();
    }
}
