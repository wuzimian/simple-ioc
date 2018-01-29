package org.wzm.simple.ioc.factory;

import org.wzm.simple.ioc.definition.BeanDefinition;

import java.util.Map;

public interface BeanFactory {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void prepareBeans();

    Object getBean(String beanName);

    Map<String,Object> getBeanMap();
}
