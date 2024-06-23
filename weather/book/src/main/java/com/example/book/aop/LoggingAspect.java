package com.example.book.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *  invoke logger whenever a service layer function called
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.book.service.*.*(..))")
    public void logBeforeMethodInvocation(JoinPoint joinPoint) {
        logger.info("Method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.book.service.*.*(..))", returning = "result")
    public void logAfterMethodInvocation(JoinPoint joinPoint, Object result) {
        logger.info("Exit: {}", joinPoint.getSignature().getName());
        logger.info("Return: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.book.service.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in method: {}", joinPoint.getSignature().getName(), error);
    }
}

