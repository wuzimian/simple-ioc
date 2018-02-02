package org.wzm.simple.ioc.aop;

import org.wzm.simple.ioc.aop.advice.Advice;
import org.wzm.simple.ioc.aop.advisor.Advisor;
import org.wzm.simple.ioc.aop.joinpoint.DefaultProceedingJoinPoint;
import org.wzm.simple.ioc.aop.joinpoint.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Chain {
    private List<Advisor> advisors;
    private int index = -1;
    private Object object;
    private Method method;
    private Object [] args;
    private ProceedingJoinPoint jp;

    public Chain(List<Advisor> advisors, Object object, Method method, Object[] args) {
        this.advisors = advisors;
        this.object = object;
        this.method = method;
        this.args = args;
        jp = new DefaultProceedingJoinPoint(object, method,args,this);
    }

    public Object execute(){
        if(index == advisors.size() -1) {
            try {
                return method.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
        index ++;
        Advisor advisor = advisors.get(index);
        Advice advice = advisor.getAdvice();
        return advice.invoke(this);
    }

    public ProceedingJoinPoint getJp() {
        return jp;
    }
}
