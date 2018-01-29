//package org.wzm.simple.ioc.aop.advisor;
//
//import org.wzm.simple.ioc.aop.advice.Advice;
//import org.wzm.simple.ioc.aop.advice.AroundAdvice;
//import org.wzm.simple.ioc.aop.pointcut.PointCut;
//
//public class AroundAdvisor implements Advisor{
//    private AroundAdvice aroundAdvice;
//    private PointCut pointCut;
//
//    public AroundAdvisor(AroundAdvice aroundAdvice, PointCut pointCut) {
//        this.aroundAdvice = aroundAdvice;
//        this.pointCut = pointCut;
//    }
//
//    public AroundAdvice getAroundAdvice() {
//        return aroundAdvice;
//    }
//
//    public void setAroundAdvice(AroundAdvice aroundAdvice) {
//        this.aroundAdvice = aroundAdvice;
//    }
//
//    @Override
//    public PointCut getPointCut() {
//        return pointCut;
//    }
//
//    public void setPointCut(PointCut pointCut) {
//        this.pointCut = pointCut;
//    }
//
//    @Override
//    public Advice getAdvice() {
//        return aroundAdvice;
//    }
//
//
//
//
//}
