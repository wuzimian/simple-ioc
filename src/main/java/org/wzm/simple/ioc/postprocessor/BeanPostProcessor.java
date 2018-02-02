package org.wzm.simple.ioc.postprocessor;

public interface BeanPostProcessor {
    Object process(Object bean);
}
