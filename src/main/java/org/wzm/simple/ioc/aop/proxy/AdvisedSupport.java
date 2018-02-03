package org.wzm.simple.ioc.aop.proxy;

import org.wzm.simple.ioc.aop.advisor.Advisor;
import org.wzm.simple.ioc.aop.pointcut.PointCut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AdvisedSupport {
    private Object targetBean;
    private List<Advisor> advisors;

    public AdvisedSupport(Object targetBean, List<Advisor> advisors) {
        this.targetBean = targetBean;
        this.advisors = advisors;
    }

    public Object getTargetBean() {
        return targetBean;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public List<Advisor> getApplicableAdvisors(Method method) {
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
