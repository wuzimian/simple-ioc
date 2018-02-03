package org.wzm.simple.ioc.aop.proxy;

import org.wzm.simple.ioc.aop.Chain;
import org.wzm.simple.ioc.aop.advisor.Advisor;
import org.wzm.simple.ioc.aop.pointcut.PointCut;
import org.wzm.simple.ioc.aop.proxy.AopProxy;
import org.wzm.simple.ioc.util.ReflectionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class JdkDynamicProxy implements InvocationHandler, AopProxy {
    private AdvisedSupport advisedSupport;

    public JdkDynamicProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                ReflectionUtil.getImplementedInterface(advisedSupport.getTargetBean()),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Advisor> applicableAdvisors = advisedSupport.getApplicableAdvisors(method);
        return new Chain(applicableAdvisors, advisedSupport.getTargetBean(), method, args).execute();
    }
}
