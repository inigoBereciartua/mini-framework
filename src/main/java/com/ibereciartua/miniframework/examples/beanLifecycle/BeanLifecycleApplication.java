package com.ibereciartua.miniframework.examples.beanLifecycle;

import com.ibereciartua.miniframework.container.ApplicationContext;

public class BeanLifecycleApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("com.ibereciartua.miniframework.examples.beanLifecycle");
        MyController myController = applicationContext.getBean(MyController.class);
        myController.sayHello();
    }
}
