package org.wzm.simple.ioc.exception;

public class DependencyInjectFailed extends RuntimeException {

    public DependencyInjectFailed(Throwable cause) {
        super(cause);
    }
}
