package com.cus.spring.annotation;

//配置类,类似原生的Spring的容器配置文件(beans.xml)

//配置扫描包路径
@ComponentScan(value = "com.cus.spring.component")
public class LsSpringConfig {
}
