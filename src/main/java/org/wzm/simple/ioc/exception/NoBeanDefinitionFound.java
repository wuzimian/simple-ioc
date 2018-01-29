package org.wzm.simple.ioc.exception;

public class NoBeanDefinitionFound extends RuntimeException {
    private String beanName;

    public NoBeanDefinitionFound(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getMessage() {
        return "no bean definition found for bean named: " + beanName + "\n";
    }
}
