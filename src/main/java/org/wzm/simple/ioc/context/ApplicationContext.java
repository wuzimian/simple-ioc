package org.wzm.simple.ioc.context;

import org.wzm.simple.ioc.factory.BeanFactory;

public interface ApplicationContext {
    Object getBean(String beanName);

    <T> T getBean(String beanName, Class<T> type);

    BeanFactory getBeanFactory();
}
