package com.ibereciartua.miniframework.aop;

import com.ibereciartua.miniframework.advices.Advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class AopInvocationHandler implements InvocationHandler {

    private final Object target;
    private final List<Advice> advices;

    public AopInvocationHandler(final Object target, final List<Advice> advices) {
        this.target = target;
        this.advices = advices;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        for (Advice advice : advices) {
            advice.before(targetMethod, args);
        }

        Object result = targetMethod.invoke(target, args);

        for (Advice advice : advices.reversed()) {
            result = advice.after(targetMethod, args, result);
        }

        return result;
    }

    public static <T> T createProxy(final T target, final Class<T> interf, final List<Advice> advices) {
        return (T) Proxy.newProxyInstance(
                interf.getClassLoader(),
                new Class[]{interf},
                new AopInvocationHandler(target, advices)
        );
    }
}
