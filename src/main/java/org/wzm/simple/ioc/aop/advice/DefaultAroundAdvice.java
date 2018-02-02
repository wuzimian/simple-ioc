package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.AspectInstanceFactory;
import org.wzm.simple.ioc.aop.Chain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultAroundAdvice extends AbstractAdvice {
    public DefaultAroundAdvice(AspectInstanceFactory aspectInstanceFactory, Method adviceMethod) {
        super(aspectInstanceFactory, adviceMethod);
    }

    @Override
    public Object invoke(Chain chain) {
        return this.invokeAdviceMethod(chain.getJp());
    }
}
