package com.meituan.spring.aop.helloworld;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ArithmeticCalculatorLoggingProxy {
    private ArithmeticCalculator target;

    public void setTarget(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculator getLogginProxy() {
        ArithmeticCalculator proxy = null;

        //代理对象由哪一个类加载器负责加载
        ClassLoader loader = target.getClass().getClassLoader();
        //代理对象的类型，即其中有哪些方法
        Class[] interfaces = new Class[]{ArithmeticCalculator.class};
        //当调用代理对象中期的方法时，该执行的代码
        InvocationHandler h = new InvocationHandler() {
            /**
             * @param proxy  正在返回的那个代理对象，一般情况下，在invoke方法中ftjbn 不使用该对象
             * @param method 正在被调用的方法
             * @param args   调用方法时，传入的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                System.out.println("The method " + methodName + "() begins with " + Arrays.asList(args));
                Object result = method.invoke(target, args);
                System.out.println("The method " + methodName + "() ends with " + result);
                return result;
            }
        };

        proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);

        return proxy;
    }
}
