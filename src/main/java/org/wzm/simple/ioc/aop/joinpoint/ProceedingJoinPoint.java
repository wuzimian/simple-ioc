package org.wzm.simple.ioc.aop.joinpoint;

public interface ProceedingJoinPoint extends JoinPoint {
    Object proceed();
}
