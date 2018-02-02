package org.wzm.simple.ioc.factory;

import org.wzm.simple.ioc.definition.BeanDefinition;
import org.wzm.simple.ioc.postprocessor.BeanPostProcessor;

import java.util.Map;

public interface BeanFactory {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void prepareBeans();

    Object getBean(String beanName);

    Map<String,BeanDefinition> getBeanDefinitionMap();
}
