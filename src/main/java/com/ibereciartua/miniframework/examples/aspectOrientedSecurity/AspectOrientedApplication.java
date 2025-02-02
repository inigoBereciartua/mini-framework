package com.ibereciartua.miniframework.examples.aspectOrientedSecurity;

import com.ibereciartua.miniframework.container.ApplicationContext;


public class AspectOrientedApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("com.ibereciartua.miniframework.examples.aspectOrientedSecurity");
        MyController myController = applicationContext.getBean(MyController.class);
        myController.sayHello();
    }
}
