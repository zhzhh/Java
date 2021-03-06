package com.spring.learn.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 2017/9/13.
 */
public class TestMain {
    public static void main(String[] args) {

        //@Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
       // ApplicationContext context = new AnnotationConfigApplicationContext(ConfigContext.class);
        ApplicationContext context = new AnnotationConfigApplicationContext(TestAutoConfiguration.class);
        //获取bean
        //TestBean tb = (TestBean) context.getBean("testBean");
        TestAutoScan tb = (TestAutoScan) context.getBean("testAutoScan");
        tb.sayHello();
        //如果加载spring-context.xml文件：
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    }
}
