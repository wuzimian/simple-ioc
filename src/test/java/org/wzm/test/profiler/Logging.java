package org.wzm.test.profiler;

import org.wzm.simple.ioc.annotation.Aspect;
import org.wzm.simple.ioc.annotation.Around;
import org.wzm.simple.ioc.annotation.Before;
import org.wzm.simple.ioc.annotation.Component;
import org.wzm.simple.ioc.aop.joinpoint.DefaultProceedingJoinPoint;
import org.wzm.simple.ioc.aop.joinpoint.JoinPoint;

import java.util.Arrays;

@Component
@Aspect
public class Logging {

    @Around(classcut = "ItemDAO", methodcut = "\\.*")
    public Object log(DefaultProceedingJoinPoint jp){
        System.out.println("before ........");
        System.out.println("target bean is: " + jp.getTargetBean());
        System.out.println("target method is: " + jp.getTargetMethod());
        System.out.println("args is: " + Arrays.toString(jp.getArgs()));
        Object object = jp.proceed();
        System.out.println("result is: " + object);
        System.out.println("after .........");
        return object;
    }

    @Before(classcut = "ItemDAO", methodcut = "\\.*")
    public void logBefore(JoinPoint jp){
        System.out.println("before 3333333333 ........");
        System.out.println("target bean is: " + jp.getTargetBean());
        System.out.println("target method is: " + jp.getTargetMethod());
        System.out.println("args is: " + Arrays.toString(jp.getArgs()));
    }

    @Before(classcut = "ItemDAO", methodcut = "\\.*")
    public void logAfter(JoinPoint jp){
        System.out.println("after after after after after ");
    }


}
