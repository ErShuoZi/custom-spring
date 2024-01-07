package com.spring.processor;

//BeanPostProcessor:参考原生Spring容器定义的接口
//该接口有两个方法postProcessBeforeInitialization / postProcessAfterInitialization
//这两个方法会对容器的所有bean生效.（切面编程）
public interface BeanPostProcessor {

    //bean的init方法前调用
    default Object postProcessBeforeInitialization(Object bean, String beanName)  {
        return bean;
    }

    //bean的init方法后调用
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
