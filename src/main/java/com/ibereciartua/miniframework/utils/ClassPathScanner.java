package com.ibereciartua.miniframework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassPathScanner {

    /**
     * Find all classes in the base package
     * @param basePackage base package to search
     * @return set of classes found
     */
    public static Set<Class<?>> findClasses(final String basePackage) {
        Set<Class<?>> classes = new HashSet<>();
        String packagePath = basePackage.replace('.', '/');

        try {
            // Get resources (files) associated with the package
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(packagePath);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());

                if (directory.exists()) {
                    // Recursively find classes in the directory
                    findClassesInDirectory(directory, basePackage, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * Recursively find classes in the directory
     * @param directory directory to search
     * @param packageName package name for classes found
     * @param classes set to store classes found
     */
    private static void findClassesInDirectory(File directory, String packageName, Set<Class<?>> classes) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                if (file.isDirectory()) {
                    findClassesInDirectory(file, packageName + "." + fileName, classes);
                } else if (fileName.endsWith(".class")) {
                    String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                    try {
                        classes.add(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Find classes with the specified annotation
     * @param basePackage base package to search
     * @param annotation annotation to search for
     * @return set of classes found with the annotation
     */
    public static Set<Class<?>> findClassesWithAnnotation(final String basePackage, final Class<? extends java.lang.annotation.Annotation> annotation) {
        Set<Class<?>> allClasses = findClasses(basePackage);
        Set<Class<?>> classesWithAnnotation = new HashSet<>();

        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(annotation)) {
                classesWithAnnotation.add(clazz);
            }
        }
        return classesWithAnnotation;
    }
}
