package com.spring.component;

import com.spring.annotation.Component;

@Component(value = "SmartDog")
public class SmartDog implements SmartAnimalable{
    @Override
    public float getSum(float i, float j) {
        float res = i + j;
        System.out.println("SmartDog-getSum-res =" + res);
        return res;
    }

    @Override
    public float getSub(float i, float j) {
        float res = i - j;
        System.out.println("SmartDog-getSub-res =" + res);
        return res;
    }
}
