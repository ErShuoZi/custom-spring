package com.spring.component;

import com.spring.annotation.Component;
import com.spring.processor.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class UserDao implements InitializingBean {
    public void hi() {
        System.out.println("UserDao's hi method was invoked~");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserDao 初始化方法执行");
    }
}
