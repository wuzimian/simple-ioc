package org.wzm.simple.ioc.aop.joinpoint;

import org.wzm.simple.ioc.aop.Chain;

import java.lang.reflect.Method;

public class DefaultProceedingJoinPoint implements ProceedingJoinPoint{
    private Object targetBean;
    private Method targetMethod;
    private Object [] args;
    private Chain chain;


    public DefaultProceedingJoinPoint(Object targetBean, Method targetMethod, Object[] args, Chain chain) {
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        this.args = args;
        this.chain = chain;
    }

    @Override
    public Object getTargetBean() {
        return targetBean;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Object proceed() {
        return chain.execute();
    }
}
