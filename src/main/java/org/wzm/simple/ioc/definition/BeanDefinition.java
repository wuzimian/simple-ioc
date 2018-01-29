package org.wzm.simple.ioc.definition;

public class BeanDefinition {
    private String name;
    private String type;

    public BeanDefinition(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
