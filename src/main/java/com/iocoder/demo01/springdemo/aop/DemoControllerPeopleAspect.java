package com.iocoder.demo01.springdemo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoControllerPeopleAspect {
    @Around("execution(* com.iocoder.demo01.springdemo.controller.DemoController.*People*(..))")
    public Object aroundGetRequest(ProceedingJoinPoint point) throws Throwable {
        System.out.println("[AROUND People] before GET for method: " + point.getSignature().getName());
        Object obj = point.proceed();
        System.out.println("[AROUND People] after GET for method: " + point.getSignature().getName());
        return obj;
    }
}
