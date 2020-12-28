package com.iocoder.demo01.springdemo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class RepoAspect {
    private Logger logger;

    private void setLogger(String className){
        logger = Logger.getLogger(className);
    }

    @Around("execution(* com.iocoder.demo01.springdemo.data.entity.RecordRepository.*(..))")
    public Object aroundGetRequest(ProceedingJoinPoint point) throws Throwable {
        if(logger==null){
            setLogger(point.getTarget().getClass().getInterfaces()[0].getName());
        }
        logger.info("[AROUND Repo] before method: " + point.getSignature().getName());
        Object obj = point.proceed();
        logger.info("[AROUND Repo] after method: " + point.getSignature().getName());
        logger.info("[AROUND Repo] args: " + obj.toString());
        logger.info("[AROUND Repo] args: " + point.getTarget().getClass().getInterfaces()[0].getName());
        return obj;
    }
}
