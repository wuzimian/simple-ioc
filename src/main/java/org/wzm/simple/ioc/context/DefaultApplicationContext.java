package org.wzm.simple.ioc.context;

import org.wzm.simple.ioc.factory.BeanFactory;
import org.wzm.simple.ioc.factory.DefaultBeanFactory;
import org.wzm.simple.ioc.definition.reader.BeanDefinitionReader;
import org.wzm.simple.ioc.definition.reader.DefaultBeanDefinitionReader;
import org.wzm.simple.ioc.postprocessor.AspectPostProcessor;

public class DefaultApplicationContext implements ApplicationContext {
    private final String basePackage;
    private BeanFactory beanFactory;

    public DefaultApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.beanFactory = new DefaultBeanFactory();
        refresh();
    }

    private void refresh(){
        prepareBeanDefinitions();
        prepareBeanFactory();
        preInitializeBeans();
    }

    private void prepareBeanDefinitions(){
        BeanDefinitionReader reader = new DefaultBeanDefinitionReader(basePackage);
        reader.resolve(beanFactory);
    }

    private void prepareBeanFactory(){
        beanFactory.registerBeanPostProcessor(new AspectPostProcessor(beanFactory));
    }

    private void preInitializeBeans(){
        beanFactory.prepareBeans();
    }

    public Object getBean(String beanName){
        return beanFactory.getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> type) {
        return type.cast(getBean(beanName));
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
