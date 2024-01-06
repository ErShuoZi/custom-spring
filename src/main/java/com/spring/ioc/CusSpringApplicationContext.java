package com.spring.ioc;

import com.cus.spring.annotation.ComponentScan;
import com.spring.annotation.Autowired;
import com.spring.annotation.Component;
import com.spring.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class CusSpringApplicationContext {
    private Class configClass;
    //beanDefinitionMap -> 存放BeanDefinition对象
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //singletonObjects -> singletonObject 单例对象
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public CusSpringApplicationContext(Class configClass) {
        beanDefinitionMapScan(configClass);
    }


    /**
     * 完成对包的扫描,并将bean的信息风撞到beanDefinition对象，再放入到Map中
     *
     * @param configClass
     */
    public void beanDefinitionMapScan(Class configClass) {
        this.configClass = configClass;
        ComponentScan componentScan = (ComponentScan) this.configClass.getDeclaredAnnotation(ComponentScan.class);
        //WillScanPackageName：要扫描的包名  com.xxx.xxx
        String WillScanPackageName = componentScan.value();
        //System.out.println(WillScanPackageName);
        //将包名格式转换/
        WillScanPackageName = WillScanPackageName.replace(".", "/");
        //获取该包名下所有class文件
        ClassLoader classLoader = CusSpringApplicationContext.class.getClassLoader();
        //System.out.println(classLoader);
        //得到完整路径
        URL resource = classLoader.getResource(WillScanPackageName);

        //io操作，查找该路径下的所有文件
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                //System.out.println(absolutePath);
                //absolutePath:/Users/ershuozi/Desktop/Java/custom-spring/target/classes/com/spring/component/UserService.class
                //要获取的是完整包名，从而进行反射，注入到singletonObjects中
                //com/spring/component/UserService
                //过滤掉所有不是.class文件的
                if (absolutePath.endsWith(".class")) {
                    String className = absolutePath.substring(absolutePath.lastIndexOf("/") + 1, absolutePath.indexOf(".class"));
                    //System.out.println(className); //UserController
                    String FullClassName = (WillScanPackageName + "/" + className).replace("/", ".");
                    //获取到了完整类名，可以进行反射
                    //检查这些类中是否要注入到容器中，即添加了注解，否则不会加入到容器中
                    try {
                        Class<?> aClass = Class.forName(FullClassName);

                        if (aClass.isAnnotationPresent(Controller.class) ||
                                aClass.isAnnotationPresent(Component.class) ||
                                aClass.isAnnotationPresent(Service.class) ||
                                aClass.isAnnotationPresent(Repository.class)) {
                            //表示注入到容器中
                            //先检查注解有没有特殊配置
                            Component component = aClass.getDeclaredAnnotation(Component.class);
                            String value = component.value();
                            if (!value.endsWith("")) {
                                className = StringUtils.uncapitalize(value);
                            }
                            BeanDefinition beanDefinition = new BeanDefinition();

                            beanDefinition.setClazz(aClass);

                            boolean isHasScope = aClass.isAnnotationPresent(Scope.class);
                            if (isHasScope) {
                                //显式配置了scope
                                Scope scope = aClass.getDeclaredAnnotation(Scope.class);
                                String scopeValue = scope.value();
                                //设置scopeValue
                                beanDefinition.setScope(scopeValue);
                            } else {
                                //没有显式配置scope
                                beanDefinition.setScope("singleton");
                                //单例 singleton
                                Object instance = aClass.newInstance();
                                singletonObjects.put(StringUtils.uncapitalize(className), instance);
                            }
                            //将beanDefinition对象装在 map中
                            beanDefinitionMap.put(StringUtils.uncapitalize(className), beanDefinition);


                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }

    public Object createBean(BeanDefinition beanDefinition) throws Exception {
        Class clazz = beanDefinition.getClazz();
        //String className = clazz.getName();
        //Class<?> aClass = Class.forName(className);
        //BeanDefinition instance = (BeanDefinition) aClass.newInstance();

        //如果有参数的话，也可以拿到构造器创建实例
        Object instance =  clazz.getDeclaredConstructor().newInstance();
        //加入依赖注入逻辑
        //得到要创建实例身上的所有字段
        Field[] WillCreateNewInstanceAllFields = instance.getClass().getDeclaredFields();
        for (Field willCreateNewInstanceAllField : WillCreateNewInstanceAllFields) {
            //判断该字段要是否进行自动组装
            if (willCreateNewInstanceAllField.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = willCreateNewInstanceAllField.getDeclaredAnnotation(Autowired.class);
                boolean required = autowired.required();
                if (required) {
                    try {
                        String FieldName = willCreateNewInstanceAllField.getName();
                        //得到了要装配属性的name后，使用该name 调用getBean方法 得到bean 进行赋值
                        Object bean = this.getBean(FieldName);
                        //通过该字段的set方法设置bean
                        //由于该字段被private修饰，需要对该字段进行爆破
                      if (bean == null) {
                          throw new RuntimeException("Bean not found for field: " + FieldName);
                      }
                        willCreateNewInstanceAllField.setAccessible(true);
                        willCreateNewInstanceAllField.set(instance,bean);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to wire field: " + willCreateNewInstanceAllField.getName(), e);
                    }
                }else {
                    String FieldName = willCreateNewInstanceAllField.getName();
                    //得到了要装配属性的name后，使用该name 调用getBean方法 得到bean 进行赋值
                    Object bean = this.getBean(FieldName);
                    if (bean != null) {
                        willCreateNewInstanceAllField.setAccessible(true);
                        willCreateNewInstanceAllField.set(instance,bean);
                    }
                }

            }
        }


        return instance;
    }

    public Object getBean(String key) throws Exception {
        //根据要获取的bean的name到beanDefinitionMap中查找
        //beanDefinitionMap中记录了哪些是单例或多例
        Object bean = null;
        if (beanDefinitionMap.containsKey(key)) {
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(key);
            if ("singleton".equalsIgnoreCase(beanDefinition.getScope())) {
                //单例
                bean = this.singletonObjects.get(key);
            } else if ("prototype".equalsIgnoreCase(beanDefinition.getScope())) {
                Object newBean = createBean(beanDefinition);
                bean = newBean;
            }

        }
        return bean;
    }
}
