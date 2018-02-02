package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.AspectInstanceFactory;
import org.wzm.simple.ioc.aop.Chain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultBeforeAdvice extends AbstractAdvice {
    public DefaultBeforeAdvice(AspectInstanceFactory aspectInstanceFactory, Method adviceMethod) {
        super(aspectInstanceFactory, adviceMethod);
    }

    @Override
    public Object invoke(Chain chain) {
        this.invokeAdviceMethod(chain.getJp());
        return chain.execute();
    }
}
