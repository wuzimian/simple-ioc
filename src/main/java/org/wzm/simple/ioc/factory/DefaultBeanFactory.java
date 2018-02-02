package org.wzm.simple.ioc.factory;

import org.wzm.simple.ioc.annotation.Inject;
import org.wzm.simple.ioc.aop.ProxyBeanCreator;
import org.wzm.simple.ioc.definition.BeanDefinition;
import org.wzm.simple.ioc.exception.DependencyInjectFailed;
import org.wzm.simple.ioc.exception.MultiBeansOfTypeFound;
import org.wzm.simple.ioc.exception.NamedBeanNotFound;
import org.wzm.simple.ioc.exception.TypeMatchedBeanNotFound;
import org.wzm.simple.ioc.postprocessor.BeanPostProcessor;
import org.wzm.simple.ioc.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap;
    private final Map<String, Object> beanMap;
    private final List<BeanPostProcessor> beanPostProcessors;


    public DefaultBeanFactory() {
        this.beanDefinitionMap = new HashMap<>();
        this.beanMap = new HashMap<>();
        this.beanPostProcessors = new ArrayList<>();
    }


    @Override
    public void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void prepareBeans() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            getBean(beanName);
        }
    }

    @Override
    public Object getBean(String beanName) {
        if(beanMap.containsKey(beanName)){
            return beanMap.get(beanName);
        }
        Object beanInstance = createBean(beanName);
        inject(beanInstance);
        beanInstance = postProcessBean(beanInstance);
        beanMap.put(beanName, beanInstance);
        return beanInstance;
    }

    private Object createBean(String beanName){
        return ReflectionUtil.getInstance(beanDefinitionMap.get(beanName).getType());
    }

    private Object postProcessBean(Object bean){
        for(BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.process(bean);
        }
        return bean;
    }

    private void inject(Object object) {
        List<Field> fieldList = Arrays.asList(object.getClass().getDeclaredFields());
        List<Field> injectedFieldList = fieldList.stream().filter(x -> x.isAnnotationPresent(Inject.class)).collect(Collectors.toList());
        for (Field field : injectedFieldList) {
            injectField(object, field);
        }
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
        List<String> typeMatchedBeans = findTypeMatchedBeans(field.getType());
        if (typeMatchedBeans.isEmpty()) {
            throw new TypeMatchedBeanNotFound(field.getType());
        } else if (typeMatchedBeans.size() != 1) {
            throw new MultiBeansOfTypeFound(field.getType());
        } else {
            try {
                Object injectedBean = getBean(typeMatchedBeans.get(0));
                field.set(object, injectedBean);
            } catch (IllegalAccessException e) {
                throw new DependencyInjectFailed(e);
            }
        }
    }

    private void injectSpecified(Object object, Field field, String injectedBeanName) {
        Object beanInstance = getBean(injectedBeanName);
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

    private List<String> findTypeMatchedBeans(Class<?> type) {
        List<String> typeMatchedBeans = new ArrayList<>();

        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (type.isAssignableFrom(entry.getValue().getBeanClazz())) {
                typeMatchedBeans.add(entry.getKey());
            }
        }
        return typeMatchedBeans;
    }
}
