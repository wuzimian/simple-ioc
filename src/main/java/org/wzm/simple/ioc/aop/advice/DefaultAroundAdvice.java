package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.Chain;
import org.wzm.simple.ioc.aop.joinpoint.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultAroundAdvice implements AroundAdvice {
    private Object adviceBean;
    private Method adviceMethod;

    public DefaultAroundAdvice(Object adviceBean, Method adviceMethod) {
        this.adviceBean = adviceBean;
        this.adviceMethod = adviceMethod;
    }

    @Override
    public Object invoke(Chain chain){
        try {
            return adviceMethod.invoke(adviceBean, chain.getJp());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
