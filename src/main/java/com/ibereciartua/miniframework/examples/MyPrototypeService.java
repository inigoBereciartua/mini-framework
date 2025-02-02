package com.ibereciartua.miniframework.examples;

import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.annotations.PostConstruct;
import com.ibereciartua.miniframework.annotations.Scope;

@Component
@Scope("prototype")
public class MyPrototypeService {

    @PostConstruct
    public void sayHello() {
        System.out.println("Hello from MyPrototypeService!");
    }
}
