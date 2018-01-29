package org.wzm.simple.ioc.context;

import org.wzm.simple.ioc.factory.BeanFactory;
import org.wzm.simple.ioc.factory.DefaultBeanFactory;
import org.wzm.simple.ioc.definition.reader.BeanDefinitionReader;
import org.wzm.simple.ioc.definition.reader.DefaultBeanDefinitionReader;

public class DefaultContext implements Context {
    private final BeanDefinitionReader beanDefinitionResolver;
    private final BeanFactory beanFactory;

    public DefaultContext(String basePackage) {
        this.beanDefinitionResolver = new DefaultBeanDefinitionReader(basePackage);
        beanFactory = new DefaultBeanFactory();
        init();
    }

    private void init(){
        beanDefinitionResolver.resolve(beanFactory);
        beanFactory.prepareBeans();
    }

    public Object getBean(String beanName){
        return beanFactory.getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> type) {
        return type.cast(getBean(beanName));
    }
}
