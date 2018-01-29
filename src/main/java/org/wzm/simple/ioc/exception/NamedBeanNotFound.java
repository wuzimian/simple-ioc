package org.wzm.simple.ioc.exception;

public class NamedBeanNotFound extends RuntimeException {
    private String beanName;

    public NamedBeanNotFound(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getMessage() {
        return "bean named " + beanName + " not found";
    }
}
