package com.ibereciartua.examples.beanLifecycle;

import com.ibereciartua.miniframework.ApplicationContext;

public class BeanLifecycleApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("com.ibereciartua.examples.beanLifecycle");
        MyController myController = applicationContext.getBean(MyController.class);
        myController.sayHello();
    }
}
