package com.ibereciartua.miniframework.examples;

import com.ibereciartua.miniframework.annotations.Autowired;
import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.annotations.Scope;

@Component
@Scope()
public class MyController {

    @Autowired
    public MyService myService;
    @Autowired
    public MyPrototypeService myPrototypeService;

    public void sayHello() {
        myService.sayHello();
    }
}
