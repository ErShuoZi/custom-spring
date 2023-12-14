package com.cus.spring.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author liushuo
 * @version 1.0
 */
@Component
@Scope(value = "prototype")  //表示以多实例的形式,返回UserControllerBean(每次getBean的时候得到的是新的)
public class UserController {

}
