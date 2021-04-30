package com.ledao.web.controller.monitor;

import com.alibaba.fastjson.JSON;
import com.ledao.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxz
 * @create 2021-03-23 9:44
 */
//AOP切面 ：多个切面时，@Order(i)注解来标识切面的优先级。i的值越小，优先级越高
@Order(5)
@Aspect
@Component
public class SysAspect {

    @Pointcut("execution(public * com.ledao.system.service.*.*(..))")
    public void log() {
    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SysAspect.class);

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (StringUtils.isNotNull(attributes)) {
            if (StringUtils.isNotNull(attributes.getRequest())) {
                HttpServletRequest request = attributes.getRequest();
               // logger.info("Aspect_URL:" + request.getRequestURL().toString());
                //logger.info("Aspect_Method:" + request.getMethod());
            }
        }
        //HttpServletRequest request = attributes.getRequest();
        //记录请求的内容
        //System.out.println("Aspect_URL:" + request.getRequestURL().toString());
        //System.out.println("Aspect_Method:" + request.getMethod());

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        //处理完请求后，返回内容
        //System.out.println("方法返回值:" + JSON.toJSONString(ret) + ",方法执行时间:" + (System.currentTimeMillis() - startTime.get()));
       // logger.info("方法返回值:" + JSON.toJSONString(ret) + ",方法执行时间:" + (System.currentTimeMillis() - startTime.get()));
    }
}
