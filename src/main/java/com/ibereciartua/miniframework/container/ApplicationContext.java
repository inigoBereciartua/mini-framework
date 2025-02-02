package com.ibereciartua.miniframework.container;

import com.ibereciartua.miniframework.annotations.Autowired;
import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.utils.ClassPathScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private Map<Class<?>, Object> beanMap = new HashMap<>();

    public ApplicationContext(final String basePackage) {
        try {
            // Step 1: Scan the package and instantiate beans.
            Set<Class<?>> classes = ClassPathScanner.findClassesWithAnnotation(basePackage, Component.class);
            for(Class<?> clazz : classes) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                beanMap.put(clazz, instance);
            }
            // Step2: Inject dependencies
            for(Object bean : beanMap.values()) {
                injectDependencies(bean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void injectDependencies(final Object bean) {
        for (var field : bean.getClass().getFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                try {
                    field.set(bean, beanMap.get(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }
}
