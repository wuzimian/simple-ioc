package org.wzm.simple.ioc.postprocessor;

import org.wzm.simple.ioc.aop.ProxyBeanCreator;
import org.wzm.simple.ioc.factory.BeanFactory;

public class AspectPostProcessor implements BeanPostProcessor{
    private BeanFactory beanFactory;
    private ProxyBeanCreator proxyBeanCreator;

    public AspectPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object process(Object bean) {
        if(proxyBeanCreator == null) {
            proxyBeanCreator = new ProxyBeanCreator(beanFactory);
        }
        return proxyBeanCreator.getProxy(bean);
    }
}
