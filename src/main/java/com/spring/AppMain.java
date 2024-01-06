package com.spring;

import com.spring.component.UserService;
import com.spring.ioc.CusSpringApplicationContext;
import com.spring.ioc.CusSpringConfig;

public class AppMain {
    public static void main(String[] args) throws Exception {
        CusSpringApplicationContext ioc = new CusSpringApplicationContext(CusSpringConfig.class);
        UserService userService = (UserService)ioc.getBean("userService");
        userService.m1();

    }
}
