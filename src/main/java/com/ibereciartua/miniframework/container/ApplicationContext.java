package com.ibereciartua.miniframework.container;

import com.ibereciartua.miniframework.annotations.*;
import com.ibereciartua.miniframework.utils.ClassPathScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private final Map<Class<?>, Class<?>> prototypeBeans = new HashMap<>();

    public ApplicationContext(final String basePackage) {
        try {
            // Step 1: Scan the package and instantiate beans.
            Set<Class<?>> classes = ClassPathScanner.findClassesWithAnnotation(basePackage, Component.class);
            for(Class<?> clazz : classes) {
                Scope scope = clazz.getAnnotation(Scope.class);
                if (scope != null && "singleton".equals(scope.value())) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    singletonBeans.put(clazz, instance);
                } else {
                    prototypeBeans.put(clazz, clazz);
                }
            }
            // Step 2: Inject dependencies
            for(Object bean : singletonBeans.values()) {
                injectDependencies(bean);
                invokePostConstructMethods(bean);
            }
            // Step 3: Register a shutdown hook to close the application context.
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Close the application context and invoke the @PreDestroy methods of the beans.
     * This method should be called when the application is shutting down.
     */
    public void close() {
        for (Object bean : singletonBeans.values()) {
            invokePreDestroyMethods(bean);
        }
    }

    private void injectDependencies(final Object bean) {
        for (var field : bean.getClass().getFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                try {
                    Object dependency = getBean(field.getType());
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void invokePostConstructMethods(final Object bean) {
        for (var method: bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                if (method.getParameters().length > 0) {
                    throw new RuntimeException("Method annotated with @PostConstruct must not have parameters: " + method);
                }
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Error invoking @PostConstruct method: " + method, e);
                }
            }
        }
    }
    private void invokePreDestroyMethods(final Object bean) {
        for (var method: bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(PreDestroy.class)) {
                if (method.getParameters().length > 0) {
                    throw new RuntimeException("Method annotated with @PreDestroy must not have parameters: " + method);
                }
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Error invoking @PreDestroy method: " + method, e);
                }
            }
        }
    }

    public <T> T getBean(final Class<T> clazz) {
        if (singletonBeans.containsKey(clazz)) {
            return clazz.cast(singletonBeans.get(clazz));
        } else {
            try {
                T instance = clazz.cast(prototypeBeans.get(clazz).getDeclaredConstructor().newInstance());
                injectDependencies(instance);
                invokePostConstructMethods(instance);
                return (T) instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Error creating prototype bean: " + clazz, e);
            }
        }
    }
}
