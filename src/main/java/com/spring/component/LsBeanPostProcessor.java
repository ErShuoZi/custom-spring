package com.spring.component;
//自定义后置处理器

import com.spring.annotation.Component;
import com.spring.processor.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Spring容器仍然将后置处理器视为Bean，仍需要注入到容器
 * 要考虑到多个后置处理器注入到容器
 */

@Component
public class LsBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //System.out.println("后置处理LsBeanPostProcessor-before方法被调用 =" + bean.getClass()+ "bean的name=" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        //System.out.println("后置处理LsBeanPostProcessor-after方法被调用 =" + bean.getClass()+ "bean的name=" + beanName);
        //实现AOP,返回代理对象,即对bean进行包装
        //指定对哪个bean进行包装
        if ("smartDog".equals(beanName)) {
           Object proxyInstance = Proxy.newProxyInstance(LsBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("method=" + method.getName());
                    Object invokeResult = null;
                    //假如前置通知的方法名是getSum
                    //可以通过注解方式动态的获取，更加灵活
                    //判断切入点
                    if ("getSum".equals(method.getName())) {
                        SmartAnimalAspect.showBeginLog();
                        invokeResult = method.invoke(bean, args);
                        //进行返回通知处理
                        SmartAnimalAspect.showSuccessLog();
                    } else {
                        invokeResult = method.invoke(bean, args);
                    }
                    return invokeResult;
                }
            });
           return proxyInstance;
        }
        return bean;
    }
}
