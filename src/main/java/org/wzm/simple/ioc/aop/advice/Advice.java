package org.wzm.simple.ioc.aop.advice;

import org.wzm.simple.ioc.aop.Chain;

public interface Advice {
    Object invoke(Chain chain);
}
