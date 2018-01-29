package org.wzm.simple.ioc.aop.advisor;

import org.wzm.simple.ioc.aop.advice.Advice;
import org.wzm.simple.ioc.aop.pointcut.PointCut;

public interface Advisor {
    Advice getAdvice();
    PointCut getPointCut();
}
