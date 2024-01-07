package com.spring.component;

/**
 * SmartAnimalAspect当作切面类使用
 */
public class SmartAnimalAspect {
    public static void showBeginLog() {
        System.out.println("前置通知");
    }

    public static void showSuccessLog() {
        System.out.println("后置通知");
    }
}
