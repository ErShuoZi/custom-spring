package com.spring.component;

import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import com.spring.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Component
@Scope(value = "prototype") //多例
public class UserService {
    //实现按照name进行组装
    @Autowired
    private UserDao userDao;
    @Autowired(required = false)
    private Car car;

    public void m1(){
        userDao.hi();
    }
}
