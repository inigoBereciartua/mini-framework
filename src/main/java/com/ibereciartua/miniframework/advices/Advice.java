package com.ibereciartua.miniframework.advices;

import java.lang.reflect.Method;

public interface Advice {
    /**
     * Called before the target method execution.
     *
     * @param method the method being invoked
     * @param args the method arguments
     */
    default void before(Method method, Object[] args) throws Throwable {}

    /**
     * Called after the target method execution.
     *
     * @param method the method that was invoked
     * @param args the method arguments
     * @param returnValue the value returned by the method
     * @return the (possibly modified) return value
     */
    default Object after(Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }
}
