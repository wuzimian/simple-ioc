package org.wzm.simple.ioc.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.wzm.simple.ioc.aop.Chain;
import org.wzm.simple.ioc.aop.advisor.Advisor;

import java.lang.reflect.Method;
import java.util.List;

public class CglibProxy implements AopProxy{
    private AdvisedSupport advisedSupport;

    public CglibProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Class<?> targetClass = advisedSupport.getTargetBean().getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new H());
        return targetClass.cast(enhancer.create());
    }

    class H implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            List<Advisor> applicableAdvisors = advisedSupport.getApplicableAdvisors(method);
            return new Chain(applicableAdvisors, advisedSupport.getTargetBean(), method, args).execute();
        }
    }
}
