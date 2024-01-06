package com.spring.ioc;

/**
 * BeanDefinition用于记录Bean的信息:
 * 1.是否单例: scope
 * 2.考虑到有多例的情况，所以有必要存储bean对应的Class对象，有了Class对象可以通过反射生成新的对象返回。这样得到的就是新的对象了，完成多例
 */
public class BeanDefinition {
    private String scope;
    private Class clazz;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "scope='" + scope + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
