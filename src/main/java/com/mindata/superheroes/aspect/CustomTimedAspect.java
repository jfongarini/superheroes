package com.mindata.superheroes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomTimedAspect {

    @Around("@annotation(com.mindata.superheroes.annotation.CustomTimed)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        log.info("Execution of method '{}' took {} ms",  joinPoint.getSignature().getName(), duration);
        return proceed;
    }
}
