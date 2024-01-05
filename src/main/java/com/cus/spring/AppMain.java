package com.cus.spring;

import com.cus.spring.annotation.LsSpringConfig;
import com.cus.spring.component.LsSpringApplicationContext;
import com.cus.spring.component.UserController;
import com.cus.spring.component.UserDao;
import com.cus.spring.component.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liushuo
 * @version 1.0
 */
public class AppMain {
    public static void main(String[] args) throws Exception {
        //测试是否可以得到容器中的bean,同时检查依赖注入是否正常
        //ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        //UserController userController = ioc.getBean("userController", UserController.class);
        //UserController userController2 = ioc.getBean("userController", UserController.class);
        //
        //UserDao userDao = (UserDao)ioc.getBean("userDao");
        //
        //UserService userService = ioc.getBean("userService", UserService.class);
        //
        //userService.m1();
        LsSpringApplicationContext ioc = new LsSpringApplicationContext(LsSpringConfig.class);
        UserController userController = (UserController)ioc.getBean("userController");



    }
}
