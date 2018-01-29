package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.Chain;
import org.wzm.simple.ioc.aop.joinpoint.JoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultBeforeAdvice implements BeforeAdvice {
    private Object adviceBean;
    private Method adviceMethod;

    public DefaultBeforeAdvice(Object adviceBean, Method adviceMethod) {
        this.adviceBean = adviceBean;
        this.adviceMethod = adviceMethod;
    }

    @Override
    public Object invoke(Chain chain){
        try {
            adviceMethod.invoke(adviceBean, chain.getJp());
            return chain.execute();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
