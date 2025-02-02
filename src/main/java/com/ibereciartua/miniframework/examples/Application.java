package com.ibereciartua.miniframework.examples;

import com.ibereciartua.miniframework.container.ApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("com.ibereciartua.miniframework.examples");
        MyController myController = applicationContext.getBean(MyController.class);
        myController.sayHello();
    }
}
