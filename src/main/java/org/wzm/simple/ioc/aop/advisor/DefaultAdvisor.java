package org.wzm.simple.ioc.aop.advisor;

import org.wzm.simple.ioc.aop.advice.Advice;
import org.wzm.simple.ioc.aop.pointcut.PointCut;

public class DefaultAdvisor implements Advisor {
    private Advice advice;
    private PointCut pointCut;

    public DefaultAdvisor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public PointCut getPointCut() {
        return pointCut;
    }
}
