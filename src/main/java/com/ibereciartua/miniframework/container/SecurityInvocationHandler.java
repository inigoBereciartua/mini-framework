package com.ibereciartua.miniframework.container;

import com.ibereciartua.miniframework.annotations.PreAuthorize;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SecurityInvocationHandler implements InvocationHandler {
    private final Object target;

    public SecurityInvocationHandler(final Object target) {
        this.target = target;
    }

    public static <T> T createProxy(T target, Class<T> interf) {
        return (T) Proxy.newProxyInstance(
                interf.getClassLoader(),
                new Class[]{interf},
                new SecurityInvocationHandler(target)
        );
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (targetMethod.isAnnotationPresent(PreAuthorize.class)) {
            PreAuthorize preAuth = targetMethod.getAnnotation(PreAuthorize.class);
            if (!checkPreAuthorization(preAuth.value())) {
                throw new SecurityException("Access denied by @PreAuthorize: " + preAuth.value());
            }
        }
        Object result = targetMethod.invoke(target, args);
        if (targetMethod.isAnnotationPresent(PreAuthorize.class)) {
            PreAuthorize postAuth = targetMethod.getAnnotation(PreAuthorize.class);
            if (!checkPostAuthorization(postAuth.value(), result)) {
                throw new SecurityException("Access denied by @PostAuthorize: " + postAuth.value());
            }
        }
        return result;
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
