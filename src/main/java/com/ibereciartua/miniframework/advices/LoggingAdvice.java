package com.ibereciartua.miniframework.advices;

import java.lang.reflect.Method;

public class LoggingAdvice implements Advice {

    @Override
    public void before(final Method method, final Object[] args) {
        System.out.println("Before method execution: " + method.getName());
    }

    @Override
    public Object after(final Method method, final Object[] args, Object returnValue) {
        System.out.println("After method execution: " + method.getName());
        return returnValue;
    }
}
