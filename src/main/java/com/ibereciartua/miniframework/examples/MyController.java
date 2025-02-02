package com.ibereciartua.miniframework.examples;

import com.ibereciartua.miniframework.annotations.Autowired;
import com.ibereciartua.miniframework.annotations.Component;

@Component
public class MyController {

    @Autowired
    public MyService myService;

    public void sayHello() {
        myService.sayHello();
    }
}
