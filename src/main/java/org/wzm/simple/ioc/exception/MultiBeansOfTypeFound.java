package org.wzm.simple.ioc.exception;

public class MultiBeansOfTypeFound extends RuntimeException {
    private Class<?> type;

    public MultiBeansOfTypeFound(Class<?> type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "multiple bean of type " + type.getName();
    }
}
