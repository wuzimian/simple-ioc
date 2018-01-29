package org.wzm.simple.ioc.exception;

public class TypeMatchedBeanNotFound extends RuntimeException {
    private Class<?> type;

    public TypeMatchedBeanNotFound(Class<?> type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "no bean of type " + type.getName();
    }
}
