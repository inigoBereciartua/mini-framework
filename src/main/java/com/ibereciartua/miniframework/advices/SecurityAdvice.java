package com.ibereciartua.miniframework.advices;

import com.ibereciartua.miniframework.annotations.PostAuthorize;
import com.ibereciartua.miniframework.annotations.PreAuthorize;

import java.lang.reflect.Method;

public class SecurityAdvice implements Advice {

    @Override
    public void before(final Method method, final Object[] args) {
        System.out.println("Checking security before method execution: " + method.getName());
        PreAuthorize preAuth = method.getAnnotation(PreAuthorize.class);
        if (!checkPreAuthorization(preAuth.value())) {
            throw new SecurityException("Access denied by @PreAuthorize: " + preAuth.value());
        }
    }

    @Override
    public Object after(final Method method, final Object[] args, Object returnValue) {
        System.out.println("Checking security after method execution: " + method.getName());
        PostAuthorize postAuth = method.getAnnotation(PostAuthorize.class);
        if (!checkPostAuthorization(postAuth.value(), returnValue)) {
            throw new SecurityException("Access denied by @PostAuthorize: " + postAuth.value());
        }
        return returnValue;
    }


    private boolean checkPreAuthorization(String expression) {
        System.out.println("Evaluating PreAuthorize: " + expression);
        return "hasRole('ROLE_ADMIN')".equals(expression);
    }

    private boolean checkPostAuthorization(String expression, Object result) {
        System.out.println("Evaluating PostAuthorize: " + expression);
        return result != null;
    }
}
