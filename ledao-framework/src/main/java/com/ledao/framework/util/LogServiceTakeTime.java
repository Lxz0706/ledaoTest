package com.ledao.framework.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author lxz
 * @create 2021-03-23 9:17
 */
@Aspect
@Component
public class LogServiceTakeTime {

    private final static Logger log = LoggerFactory.getLogger(LogServiceTakeTime.class);

    @Pointcut("execution(* com.leecx.service..*.*(..))")
    public void performance() {
    }

    @Around("performance()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //记录起始时间
        long begin = System.currentTimeMillis();
        Object result = "";
        /** 执行目标方法 */
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("日志记录发生错误, errorMessage: {}", e.getMessage());
        } finally {
            /** 记录操作时间 */
            long took = System.currentTimeMillis() - begin;
            if (took >= 10000) {
                log.error("Service 执行时间为: {}秒", took);
                //                log.error("Controller 执行时间为: {}毫秒", took);
            } else if (took >= 5000) {
                log.warn("Service 执行时间为: {}秒", took);
                //                log.warn("Controlle r执行时间为: {}毫秒", took);
            } else if (took >= 3000) {
                log.info("Service执行时间为: {}秒", took);
                //                log.info("Controller 执行时间为: {}毫秒", took);
            }
            // TODO 日志保存到MongoDB中
        }
        return result;
    }

    @Before("performance()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        log.info("doBefore");
    }

    @AfterReturning(returning = "ret", pointcut = "performance()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("doAfterReturning");
    }

}
