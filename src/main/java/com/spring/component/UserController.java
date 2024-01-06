package com.spring.component;

import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component
public class UserController {
    @Autowired
    private UserService userService1;
}
