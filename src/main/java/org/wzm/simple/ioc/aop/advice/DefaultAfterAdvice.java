package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.Chain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultAfterAdvice implements AfterAdvice {
    private Object adviceBean;
    private Method adviceMethod;

    public DefaultAfterAdvice(Object adviceBean, Method adviceMethod) {
        this.adviceBean = adviceBean;
        this.adviceMethod = adviceMethod;
    }

    @Override
    public Object invoke(Chain chain){
        try {
            Object obj = chain.execute();
            adviceMethod.invoke(adviceBean, chain.getJp());
            return obj;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
