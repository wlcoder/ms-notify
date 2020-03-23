package com.wl.msnotify.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class TimeConsumeAspect {

    @Pointcut("@annotation(com.wl.msnotify.aop.TimeConsumeAnnotation)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("[方法耗时]" + methodSignature.getDeclaringTypeName() + "." + methodSignature.getMethod().getName() + " 耗时: " + (endTime - startTime) + "毫秒");
        return proceed;
    }

}
