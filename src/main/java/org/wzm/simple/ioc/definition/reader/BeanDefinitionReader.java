package org.wzm.simple.ioc.definition.reader;


import org.wzm.simple.ioc.factory.BeanFactory;

public interface BeanDefinitionReader {
    void resolve(BeanFactory beanFactory);
}
