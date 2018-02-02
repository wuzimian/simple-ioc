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
import org.wzm.simple.ioc.definition.BeanDefinition;
import org.wzm.simple.ioc.factory.BeanFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyBeanCreator {
    private List<Advisor> advisors;
    private BeanFactory beanFactory;

    public ProxyBeanCreator(BeanFactory beanFactory) {
        advisors = new ArrayList<>();
        this.beanFactory = beanFactory;
    }

    public Object getProxy(Object bean) {
        if(advisors.isEmpty()) {
            initialize();
        }

        List<Advisor> candidates = candidateAdvisors(bean);
        if(candidates.isEmpty()){
            return bean;
        }

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                getImplementedInterface(bean),
                new JdkProxy(bean, candidates));
    }

    private void initialize() {

        List<Map.Entry<String, BeanDefinition>> aspects = beanFactory.getBeanDefinitionMap().entrySet().stream()
                .filter(x->isAspect(x.getValue().getBeanClazz()))
                .collect(Collectors.toList());

        for (Map.Entry<String, BeanDefinition> entry : aspects) {
            BeanDefinition aspect = entry.getValue();
            advisors.addAll(getAdvisors(aspect));
        }
    }

    private List<Advisor> candidateAdvisors(Object bean){
        List<Advisor> candidates = new ArrayList<>();
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Advisor advisor : advisors) {
            if(advisor.getPointCut().classMatches(bean)){
                for(Method method : methods) {
                    if(advisor.getPointCut().methodMatches(method)){
                        candidates.add(advisor);
                        break;
                    }
                }
            }
        }
        return candidates;
    }

    private boolean isAspect(Class<?> clazz) {
        return clazz.isAnnotationPresent(Aspect.class) ? true : false;
    }

    private List<Advisor> getAdvisors(BeanDefinition beanDefinition) {
        List<Advisor> advisors = new ArrayList<>();
        Class<?> clazz = beanDefinition.getBeanClazz();
        Method[] methods = clazz.getDeclaredMethods();
        AspectInstanceFactory aspectInstanceFactory = new DefaultAspectInstanceFactory(beanFactory, beanDefinition.getName());
        for (Method method : methods) {
            if (method.isAnnotationPresent(Around.class)) {
                String classCut = method.getAnnotation(Around.class).classcut();
                String methodCut = method.getAnnotation(Around.class).methodcut();
                Advice advice = new DefaultAroundAdvice(aspectInstanceFactory, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(advice, pointCut);
                advisors.add(advisor);
            } else if(method.isAnnotationPresent(Before.class)){
                String classCut = method.getAnnotation(Before.class).classcut();
                String methodCut = method.getAnnotation(Before.class).methodcut();
                Advice advice = new DefaultBeforeAdvice(aspectInstanceFactory, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(advice, pointCut);
                advisors.add(advisor);
            } else if(method.isAnnotationPresent(After.class)){
                String classCut = method.getAnnotation(After.class).classcut();
                String methodCut = method.getAnnotation(After.class).methodcut();
                Advice advice = new DefaultAfterAdvice(aspectInstanceFactory, method);
                PointCut pointCut = new DefaultPointCut(classCut, methodCut);
                Advisor advisor = new DefaultAdvisor(advice, pointCut);
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
