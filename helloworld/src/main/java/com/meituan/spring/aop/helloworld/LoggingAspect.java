package com.meituan.spring.aop.helloworld;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 * 日志切面
 *
 * @author Alcalde
 * @create 2018-06-29 下午9:20
 */

//把这个类声明为一个切面
@Component
@Aspect
public class LoggingAspect {

    //声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("execution(public int com.meituan.spring.aop.helloworld.ArithmeticCalculatorImpl.*(int, int))")
    public void beforeMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + signature.getName()  + args + " begins");
    }

    @After("execution(* com.meituan.spring.aop.helloworld.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String signatureName = signature.getName();
        System.out.println("The method " + signatureName + "() ends");
    }

    @AfterReturning(value = "execution(* com.meituan.spring.aop.helloworld.*.*(..))",
        returning = "result")
    public void afterMethodReturning(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + signature.getName()  + args + " ends with " + result);
    }

    /**
     * 在方法出现异常时会执行的代码，可以访问到异常对象，且可以指定在出现特定异常时再执行通知代码
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "execution(* com.meituan.spring.aop.helloworld.*.*(..))",
            throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + signature.getName()  + args + " occurs exception " + ex.getMessage());
    }
}
