package com.iocoder.demo01.springdemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoControllerAspect {

    @Before("execution(* com.iocoder.demo01.springdemo.controller.DemoController.get*(..))")
    public void beforeGetRequest(JoinPoint point) {
        System.out.println("[BEFORE] GET method: " + point.getSignature().getName());
    }

    @Around("execution(* com.iocoder.demo01.springdemo.controller.DemoController.get*(..))")
    public Object aroundGetRequest(ProceedingJoinPoint point) throws Throwable {
        System.out.println("[AROUND] before GET for method: " + point.getSignature().getName());
        Object obj = point.proceed();
        System.out.println("[AROUND] after GET for method: " + point.getSignature().getName());
        return obj;
    }

    @After("execution(* com.iocoder.demo01.springdemo.controller.DemoController.get*(..))")
    public void afterGetRequest(JoinPoint point) {
        System.out.println("[AFTER] GET for method: " + point.getSignature().getName());
    }

}
