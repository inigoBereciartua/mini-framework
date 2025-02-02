package com.ibereciartua.examples.aspectOrientedSecurity;

import com.ibereciartua.miniframework.annotations.Autowired;
import com.ibereciartua.miniframework.annotations.Component;
import com.ibereciartua.miniframework.annotations.Scope;

@Component
@Scope
public class MyController {
    @Autowired
    public MySecuredService mySecuredService;

    public void sayHello() {
        System.out.println(mySecuredService.secureMethod("Hello"));
    }
}
