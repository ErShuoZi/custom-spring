package com.spring.component;

import com.spring.annotation.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class UserDao {
    public void hi() {
        System.out.println("UserDao's hi method was invoked~");
    }
}
