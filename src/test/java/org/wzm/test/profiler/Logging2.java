package org.wzm.test.profiler;

import org.wzm.simple.ioc.annotation.Aspect;
import org.wzm.simple.ioc.annotation.Around;
import org.wzm.simple.ioc.annotation.Component;
import org.wzm.simple.ioc.aop.joinpoint.DefaultProceedingJoinPoint;

@Component
@Aspect
public class Logging2 {

    @Around(classcut = "ServiceImpl", methodcut = "\\.*")
    public Object log(DefaultProceedingJoinPoint jp){
        System.out.println("before 22222 ........");
        Object object = jp.proceed();
        System.out.println("result 2222 is: " + object);
        System.out.println("after 22222.........");
        return object;
    }
}
