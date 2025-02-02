package com.ibereciartua.miniframework.examples;

import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.annotations.PostConstruct;
import com.ibereciartua.miniframework.annotations.PreDestroy;

@Component
public class MyService {

        public void sayHello() {
            System.out.println("Hello from MyService!");
        }

        @PostConstruct
        public void init() {
            System.out.println("MyService initialized!");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("MyService destroyed!");
        }
}
