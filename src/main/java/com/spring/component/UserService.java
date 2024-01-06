package com.spring.component;

import com.spring.annotation.Component;
import com.spring.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Component
@Scope(value = "prototype") //多例
public class UserService {
}
