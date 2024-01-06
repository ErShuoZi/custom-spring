package com.spring;

import com.spring.ioc.CusSpringApplicationContext;
import com.spring.ioc.CusSpringConfig;

public class AppMain {
    public static void main(String[] args) throws Exception {
        CusSpringApplicationContext ioc = new CusSpringApplicationContext(CusSpringConfig.class);
        Object userService = ioc.getBean("userService");
        Object userService2 = ioc.getBean("userService");
        Object userService3 = ioc.getBean("userService1");
        System.out.println(userService);
        System.out.println(userService2);
        System.out.println(userService3);

        System.out.println("--------------");
        Object userDao = ioc.getBean("userDao");
        Object userDao2 = ioc.getBean("userDao");
        System.out.println(userDao);
        System.out.println(userDao2);
    }
}
