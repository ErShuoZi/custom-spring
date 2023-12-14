package com.cus.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author liushuo
 * @version 1.0
 */
@Component  //也可以使用@Service
public class UserService {
    //定义属性
    @Autowired //也可以使用@Resource   通过 @Autowired 注解 实现依赖注入

    private UserDao userDao;

    public void m1() {
        userDao.hi();
    }


    //由于不是通过手动配置xml进行注入,而是自动注入,所以配置后置处理器需要使用注解方式
    @PostConstruct
    public void init() {
        System.out.println("UserService的init方法执行了");
    }
}
