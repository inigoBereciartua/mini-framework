package com.ibereciartua.examples.aspectOrientedSecurity;

import com.ibereciartua.miniframework.ApplicationContext;


public class AspectOrientedApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("com.ibereciartua.examples.aspectOrientedSecurity");
        MyController myController = applicationContext.getBean(MyController.class);
        myController.sayHello();
    }
}
