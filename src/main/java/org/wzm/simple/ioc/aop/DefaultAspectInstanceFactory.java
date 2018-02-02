package org.wzm.simple.ioc.aop;

import org.wzm.simple.ioc.factory.BeanFactory;

public class DefaultAspectInstanceFactory implements AspectInstanceFactory{
    private BeanFactory beanFactory;
    private String name;

    public DefaultAspectInstanceFactory(BeanFactory beanFactory, String name) {
        this.beanFactory = beanFactory;
        this.name = name;
    }

    @Override
    public Object getAspectInstance() {
        return beanFactory.getBean(name);
    }
}
