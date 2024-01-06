package com.spring.processor;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
