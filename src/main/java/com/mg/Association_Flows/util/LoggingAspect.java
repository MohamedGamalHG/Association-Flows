package com.mg.Association_Flows.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    @Around("execution(* com.mg.Association_Flows.*.*(..)")
    @Around("execution(* com.mg.Association_Flows..*(..)")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName =
                joinPoint.getSignature().getName();

        logger.info("Method started: {}", methodName);

        long start = System.currentTimeMillis();

        try {

            Object result = joinPoint.proceed();

            logger.info(
                    "Method finished: {} returned {}",
                    methodName,
                    result
            );

            return result;

        } catch (Exception ex) {

            logger.error(
                    "Exception in method {} : {}",
                    methodName,
                    ex.getMessage()
            );

            throw ex;

        } finally {

            long end = System.currentTimeMillis();

            logger.info(
                    "Execution time for {} : {} ms",
                    methodName,
                    (end - start)
            );
        }
    }
}
