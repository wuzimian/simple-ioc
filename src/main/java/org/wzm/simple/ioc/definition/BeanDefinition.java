package org.wzm.simple.ioc.definition;

public class BeanDefinition {
    private String name;
    private String type;
    private Class<?> beanClazz;

    public BeanDefinition(String name, String type, Class<?> beanClazz) {
        this.name = name;
        this.type = type;
        this.beanClazz = beanClazz;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Class<?> getBeanClazz() {
        return beanClazz;
    }
}
