package com.cus.spring.component;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author liushuo
 * @version 1.0
 */
@Component(value = "Haha") //可以以使用@Repository

public class UserDao {
    public void hi() {
        System.out.println("UserDao - hi~");
    }
}
