//package org.wzm.simple.ioc.aop.advisor;
//
//import org.wzm.simple.ioc.aop.advice.Advice;
//import org.wzm.simple.ioc.aop.advice.BeforeAdvice;
//import org.wzm.simple.ioc.aop.pointcut.PointCut;
//
//public class BeforeAdvisor implements Advisor{
//    private BeforeAdvice beforeAdvice;
//    private PointCut pointCut;
//
//    public BeforeAdvisor(BeforeAdvice beforeAdvice, PointCut pointCut) {
//        this.beforeAdvice = beforeAdvice;
//        this.pointCut = pointCut;
//    }
//
//    @Override
//    public Advice getAdvice() {
//        return beforeAdvice;
//    }
//
//    @Override
//    public PointCut getPointCut() {
//        return pointCut;
//    }
//}
