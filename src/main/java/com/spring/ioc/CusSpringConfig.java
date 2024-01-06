package com.spring.ioc;

import com.cus.spring.annotation.ComponentScan;

//等价于原生Spring的xml
//@ComponentScan(value = ) 获取要扫描的包的路径
@ComponentScan(value = "com.spring.component")
public class CusSpringConfig {
}
