package com.baixs.demo.aop;

import com.baixs.demo.domain.SysLog;
import com.baixs.demo.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class SysLogAspect {
    @Pointcut("@annotation(com.baixs.demo.aop.SysLogAnnotation)")
    public void logPointcut() {

    }

    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long takeTime = (System.currentTimeMillis() - startTime) / 1000;
        saveSysLog(joinPoint, takeTime);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long takeTime) throws JsonProcessingException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog sysLog = new SysLog();
        SysLogAnnotation sysLogAnnotation = method.getAnnotation(SysLogAnnotation.class);
        if (sysLogAnnotation != null) {
            String value = sysLogAnnotation.value();
            sysLog.setOperation(value);
        }
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        //sysLog.setParams(JsonUtil.Json2String(args));
        System.out.println(JsonUtil.Json2String(sysLog));
    }
}
