package com.spring;

import com.spring.component.SmartAnimalable;
import com.spring.component.SmartDog;
import com.spring.component.UserService;
import com.spring.ioc.CusSpringApplicationContext;
import com.spring.ioc.CusSpringConfig;

public class AppMain {
    public static void main(String[] args) throws Exception {
        CusSpringApplicationContext ioc = new CusSpringApplicationContext(CusSpringConfig.class);
        SmartAnimalable smartDog = (SmartAnimalable)ioc.getBean("smartDog");
        System.out.println(smartDog.getClass());
        smartDog.getSum(1,2);

    }
}
