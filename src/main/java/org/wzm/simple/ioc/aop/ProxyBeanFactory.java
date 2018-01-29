package org.wzm.simple.ioc.aop;

import org.wzm.simple.ioc.annotation.After;
import org.wzm.simple.ioc.annotation.Around;
import org.wzm.simple.ioc.annotation.Aspect;
import org.wzm.simple.ioc.annotation.Before;
import org.wzm.simple.ioc.aop.advice.*;
import org.wzm.simple.ioc.aop.advisor.Advisor;
import org.wzm.simple.ioc.aop.advisor.DefaultAdvisor;
import org.wzm.simple.ioc.aop.pointcut.DefaultPointCut;
import org.wzm.simple.ioc.aop.pointcut.PointCut;
import org.wzm.simple.ioc.factory.BeanFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyBeanFactory {
    private List<Advisor> advisors;
    private BeanFactory beanFactory;

    public ProxyBeanFactory(BeanFactory beanFactory) {
        advisors = new ArrayList<>();
        this.beanFactory = beanFactory;
    }

    public Object getProxy(Object bean) {
        if(advisors.isEmpty()) {
            initialize();
        }
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                getImplementedInterface(bean),
                new JdkProxy(bean, advisors));
    }

    private void initialize() {
        List<Map.Entry<String, Object>> aspectBeans = beanFactory.getBeanMap().entrySet().stream()
                .filter(x -> isAspect(x.getValue()))
                .collect(Collectors.toList());

        for (Map.Entry<String, Object> entry : aspectBeans) {
            Object aspect = entry.getValue();
            advisors.addAll(getAdvisors(aspect));
        }
    }

    private boolean isAspect(Object object) {
        Class<?> clazz = object.getClass();
        return clazz.isAnnotationPresent(Aspect.class) ? true : false;
    }

    private List<Advisor> getAdvisors(Object object) {
        List<Advisor> advisors = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Around.class)) {
                String classCut = method.getAnnotation(Around.class).classcut();
                String methodCut = method.getAnnotation(Around.class).methodcut();
                AroundAdvice aroundAdvice = new DefaultAroundAdvice(object, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(aroundAdvice, pointCut);
                advisors.add(advisor);
            } else if(method.isAnnotationPresent(Before.class)){
                String classCut = method.getAnnotation(Before.class).classcut();
                String methodCut = method.getAnnotation(Before.class).methodcut();
                BeforeAdvice beforeAdvice = new DefaultBeforeAdvice(object, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(beforeAdvice, pointCut);
                advisors.add(advisor);
            } else if(method.isAnnotationPresent(After.class)){
                String classCut = method.getAnnotation(After.class).classcut();
                String methodCut = method.getAnnotation(After.class).methodcut();
                AfterAdvice afterAdvice = new DefaultAfterAdvice(object, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(afterAdvice, pointCut);
                advisors.add(advisor);
            }
        }
        return advisors;
    }

    private Class<?>[] getImplementedInterface(Object object) {
        Class<?> clazz = object.getClass();
        return clazz.getInterfaces();
    }

}
