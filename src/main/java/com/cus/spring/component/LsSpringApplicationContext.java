package com.cus.spring.component;
import com.cus.spring.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LsSpringApplicationContext 类似于 Spring 原生IOC容器
 */
public class LsSpringApplicationContext {
    //容器,存放的是通过反射创建的对象（基于注解）
    private ConcurrentHashMap<String,Object> ioc =
            new ConcurrentHashMap<>();
    //获取要反射的Class类型（配置类） -> 通过配置类得到注解 ->得到注解，获取注解的value，也就是要扫描的包 -> 获取扫描包下的所有.class文件
    private Class configClass;

    public LsSpringApplicationContext(Class configClass) throws Exception{
        this.configClass = configClass;
        //获取扫描包
        ComponentScan componentScan = (ComponentScan)this.configClass.getDeclaredAnnotation(ComponentScan.class);
        String packageNameWillScan = componentScan.value();
        //获取扫描包下的所有类/class
        //1.通过类加载器
        ClassLoader classLoader = LsSpringApplicationContext.class.getClassLoader();
        //2.通过类的加载器获取到要扫描包的url
        //getResource参数按照 /
        //packageNameWillScan中的是以 . 分割的
        packageNameWillScan = packageNameWillScan.replace(".","/");
        //System.out.println(packageNameWillScan); //com/cus/spring/component
        URL resource = classLoader.getResource("com/cus/spring/component");
        //3.将要加载的资源(.class) 路径下的文件进行遍历 -> IO
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileAbsolutePath = f.getAbsolutePath();
                ///Users/ershuozi/Desktop/Java/custom-spring/target/classes/com/cus/spring/component/LsSpringApplicationContext.class
                //System.out.println(fileAbsolutePath);
                //要获取到的是com/cus/spring/component/LsSpringApplicationContext.class -> 截取 -> 以 .分割
                //获取到类名
                if (fileAbsolutePath.endsWith(".class")) {
                    String className = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/") + 1, fileAbsolutePath.indexOf(".class"));
                    //System.out.println(className);  //LsSpringApplicationContext
                    String WillReflexClassPackageName = (packageNameWillScan +"/" + className).replace("/",".");

                    //判断该类是否要注入到容器
                    //Class.forName - classLoader.loadClass
                    //Class.forName 反射加载类，得到对象, 该方式回去执行该类的静态方法
                    //classLoader.loadClass 反射类的Class,不会执行该类的静态方法
                    Class<?> aClass = classLoader.loadClass(WillReflexClassPackageName);
                    if (aClass.isAnnotationPresent(Component.class) ||aClass.isAnnotationPresent( Service.class)|| aClass.isAnnotationPresent( Repository.class) ||aClass.isAnnotationPresent( Controller.class)) {
                       if (aClass.isAnnotationPresent(Component.class)) {
                           Component component = aClass.getDeclaredAnnotation(Component.class);
                           String id = component.value();
                           if (!"".endsWith(id)) {
                             className = id;
                               System.out.println(className);
                           }
                       }
                        //表示有注解，注入到容器中
                        //反射创建对象
                        Object instance = aClass.newInstance();
                        ioc.put(StringUtils.uncapitalize(className),instance);
                    }

                }
            }
        }
    }

    public Object getBean(String key) {
        return ioc.get(key);
    }
}
