package org.wzm.simple.ioc.aop;

import org.wzm.simple.ioc.aop.advisor.Advisor;
import org.wzm.simple.ioc.aop.pointcut.PointCut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JdkProxy implements InvocationHandler {
    private Object targetBean;
    private List<Advisor> advisors;

    public JdkProxy(Object targetBean, List<Advisor> advisors) {
        this.targetBean = targetBean;
        this.advisors = advisors;
    }

    public Object getTargetBean() {
        return targetBean;
    }

    public void setTargetBean(Object targetBean) {
        this.targetBean = targetBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Advisor> applicableAdvisors = getApplicableAdvisors(method);
        return new Chain(applicableAdvisors, targetBean, method, args).execute();
    }

    private List<Advisor> getApplicableAdvisors(Method method) {
        List<Advisor> applicableAdvisors = new ArrayList<>();
        for (Advisor advisor : advisors) {
            PointCut pointCut = advisor.getPointCut();
            if (pointCut.classMatches(targetBean)) {
                if (pointCut.methodMatches(method)) {
                    applicableAdvisors.add(advisor);
                }
            }
        }
        return applicableAdvisors;
    }
}
