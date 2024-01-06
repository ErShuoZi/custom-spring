package com.spring.component;

import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import com.spring.annotation.Scope;
import com.spring.processor.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@Component
@Scope(value = "prototype") //多例
public class UserService implements InitializingBean {
    //实现按照name进行组装
    @Autowired
    private UserDao userDao;
    @Autowired(required = false)
    private Car car;

    public void m1(){
        userDao.hi();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userService初始化方法被执行");
    }
}
