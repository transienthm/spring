package com.meituan.spring.aop.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

public class Main {

    public static void main(String[] args) {

        //1. 创建Spring的IOC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        //2. 从IOC容器中获取bean的实例

        ArithmeticCalculator arithmeticCalculator = ctx.getBean(ArithmeticCalculator.class);

        //3. 使用bean
        int result = arithmeticCalculator.add(3, 4);
        System.out.println("result = " + result);

    }
}
