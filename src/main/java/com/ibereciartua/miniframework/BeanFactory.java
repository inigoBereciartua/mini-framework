package com.ibereciartua.miniframework;

import com.ibereciartua.miniframework.advices.Advice;
import com.ibereciartua.miniframework.advices.LoggingAdvice;
import com.ibereciartua.miniframework.advices.SecurityAdvice;
import com.ibereciartua.miniframework.annotations.PostAuthorize;
import com.ibereciartua.miniframework.annotations.PreAuthorize;
import com.ibereciartua.miniframework.aop.AopInvocationHandler;

import java.util.Arrays;
import java.util.List;

public class BeanFactory {

    private static final List<Advice> globalAdvices = List.of(
            new LoggingAdvice(),
            new SecurityAdvice()
    );

    public static <T> T createBean(Class<T> clazz) {
        try {
            T bean = clazz.getDeclaredConstructor().newInstance();

            // Check if any method has any advice-triggering annotation (this is up to your design).
            boolean hasAopAnnotations = Arrays.stream(bean.getClass().getMethods())
                    .anyMatch(method -> method.isAnnotationPresent(PreAuthorize.class)
                                    || method.isAnnotationPresent(PostAuthorize.class)
                            // You can also check for a custom logging annotation, transactional annotation, etc.
                    );

            if (hasAopAnnotations) {
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces.length > 0) {
                    // For simplicity, use the first interface as the proxy interface.
                    bean = AopInvocationHandler.createProxy(bean, (Class<T>) interfaces[0], globalAdvices);
                } else {
                    System.out.println("Warning: " + clazz.getName() + " does not implement any interfaces. Skipping proxy creation.");
                }
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("Error creating bean: " + clazz, e);
        }
    }

}
