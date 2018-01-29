package org.wzm.simple.ioc.factory;

import org.wzm.simple.ioc.annotation.Inject;
import org.wzm.simple.ioc.aop.JdkProxy;
import org.wzm.simple.ioc.aop.ProxyBeanFactory;
import org.wzm.simple.ioc.definition.BeanDefinition;
import org.wzm.simple.ioc.exception.DependencyInjectFailed;
import org.wzm.simple.ioc.exception.MultiBeansOfTypeFound;
import org.wzm.simple.ioc.exception.NamedBeanNotFound;
import org.wzm.simple.ioc.exception.TypeMatchedBeanNotFound;
import org.wzm.simple.ioc.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap;
    private final Map<String, Object> beanMap;
    private final ProxyBeanFactory proxyBeanFactory;


    public DefaultBeanFactory() {
        this.beanDefinitionMap = new HashMap<>();
        this.beanMap = new HashMap<>();
        this.proxyBeanFactory = new ProxyBeanFactory(this);
    }

    @Override
    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void prepareBeans() {
        initBeans();
        initProxyBeans();
        injectBeans();
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    private void initProxyBeans() {
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object proxyBean = proxyBeanFactory.getProxy(entry.getValue());
            beanMap.put(entry.getKey(), proxyBean);
        }
    }

//    private Map<String, Object> getProxyBeans(Object aspectBean) {
//        Map<String, Object> proxyBeanMap = new HashMap<>();
//
//        if (!isAspect(aspectBean)) {
//            return proxyBeanMap;
//        }
//
//        List<Advice> adviceList = getAdvices(aspectBean);
//
//        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
//            String beanName = entry.getKey();
//            Object bean = entry.getValue();
//            if (isAspect(bean)) {
//                continue;
//            }
//
//            Map<String, List<AroundAdvisor>> applicableBeforeAdvice = getApplicableBeforeAdvice(bean, adviceList);
//            if (!applicableBeforeAdvice.isEmpty()) {
//                if (bean instanceof Proxy) {
//                    JdkProxy jdkProxy = (JdkProxy) Proxy.getInvocationHandler(bean);
//                    jdkProxy.addAdvices(applicableBeforeAdvice);
//                } else {
//                    Object proxyObj = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                            getImplementedInterface(bean),
//                            new JdkProxy(bean, applicableBeforeAdvice));
//                    proxyBeanMap.put(beanName, proxyObj);
//                }
//            }
//        }
//        return proxyBeanMap;
//    }

//    private Class<?>[] getImplementedInterface(Object object) {
//        Class<?> clazz = object.getClass();
//        return clazz.getInterfaces();
//    }
//
//    private List<Advice> getAdvices(Object object) {
//        List<Advice> advice = new ArrayList<>();
//        Class<?> clazz = object.getClass();
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.isAnnotationPresent(Around.class)) {
//                String pointCut = method.getAnnotation(Around.class).classcut();
//                String methodCut = method.getAnnotation(Around.class).methodcut();
//                advice.add(new AroundAdvisor(object, method, pointCut, methodCut));
//            }
//        }
//        return advice;
//    }

//    private Map<String, List<AroundAdvisor>> getApplicableBeforeAdvice(Object bean, List<Advice> adviceList) {
//        if (bean instanceof Proxy) {
//            bean = findTargetBean(bean);
//        }
//        Class<?> clazz = bean.getClass();
//        String clazzName = clazz.getName();
//
//        Map<String, List<AroundAdvisor>> applicableBeforeAdvices = new HashMap<>();
//        for (Advice advice : adviceList) {
//            if (!(advice instanceof AroundAdvisor)) {
//                continue;
//            }
//
//            String classCut = ((AroundAdvisor) advice).getClassCut();
//            Pattern classPattern = Pattern.compile(classCut);
//            Matcher classMatcher = classPattern.matcher(clazzName);
//            if (classMatcher.find()) {
//                String methodCut = ((AroundAdvisor) advice).getMethodCut();
//                Pattern methodPattern = Pattern.compile(methodCut);
//                Method[] methods = clazz.getDeclaredMethods();
//                for (Method method : methods) {
//                    String methodName = method.getName();
//                    Matcher methodMatcher = methodPattern.matcher(methodName);
//                    if (methodMatcher.find()) {
//                        if (applicableBeforeAdvices.get(method) != null) {
//                            applicableBeforeAdvices.get(method).add((AroundAdvisor) advice);
//                        } else {
//                            List<AroundAdvisor> aroundAdvisors = new ArrayList<>();
//                            aroundAdvisors.add((AroundAdvisor) advice);
//                            applicableBeforeAdvices.put(method.getName(), aroundAdvisors);
//                        }
//                    }
//                }
//            }
//        }
//        return applicableBeforeAdvices;
//    }

//    private boolean isAspect(Object object) {
//        Class<?> clazz = object.getClass();
//        return clazz.isAnnotationPresent(Aspect.class) ? true : false;
//    }

    private void initBeans() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            String type = entry.getValue().getType();
            Object newInstance = ReflectionUtil.getInstance(type);
            beanMap.put(beanName, newInstance);
        }
    }

    private void injectBeans() {
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object beanInstance = entry.getValue();
            inject(beanInstance);
        }
    }

    private void inject(Object object) {
        if (object instanceof Proxy) {
            object = findTargetBean(object);
        }
        List<Field> fieldList = Arrays.asList(object.getClass().getDeclaredFields());
        List<Field> injectedFieldList = fieldList.stream().filter(x -> x.isAnnotationPresent(Inject.class)).collect(Collectors.toList());
        for (Field field : injectedFieldList) {
            injectField(object, field);
        }
    }

    private Object findTargetBean(Object object) {
        if (!(object instanceof Proxy)) {
            return object;
        }
        Proxy proxy = (Proxy) object;
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
        JdkProxy jdkProxy = (JdkProxy) invocationHandler;
        return jdkProxy.getTargetBean();
    }

    private void injectField(Object object, Field field) {
        field.setAccessible(true);
        String value = field.getAnnotation(Inject.class).value();
        if (value.isEmpty()) {
            injectTypeMatched(object, field);
        } else {
            injectSpecified(object, field, value);
        }
    }

    private void injectTypeMatched(Object object, Field field) {
        List<Object> typeMatchedBeans = findTypeMatchedBeans(field.getType());
        if (typeMatchedBeans.isEmpty()) {
            throw new TypeMatchedBeanNotFound(field.getType());
        } else if (typeMatchedBeans.size() != 1) {
            throw new MultiBeansOfTypeFound(field.getType());
        } else {
            try {
                field.set(object, typeMatchedBeans.get(0));
            } catch (IllegalAccessException e) {
                throw new DependencyInjectFailed(e);
            }
        }
    }

    private void injectSpecified(Object object, Field field, String injectedBeanName) {
        Object beanInstance = beanMap.get(injectedBeanName);
        if (beanInstance == null) {
            throw new NamedBeanNotFound(injectedBeanName);
        } else {
            try {
                field.set(object, beanInstance);
            } catch (IllegalAccessException e) {
                throw new DependencyInjectFailed(e);
            }
        }
    }

    private List<Object> findTypeMatchedBeans(Class<?> type) {
        List<Object> typeMatchedBeans = new ArrayList<>();

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (type.isInstance(entry.getValue())) {
                typeMatchedBeans.add(entry.getValue());
            }
        }
        return typeMatchedBeans;
    }
}
