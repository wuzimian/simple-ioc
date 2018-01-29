package org.wzm.simple.ioc.context;

public interface Context {
    Object getBean(String beanName);

    <T> T getBean(String beanName, Class<T> type);
}
