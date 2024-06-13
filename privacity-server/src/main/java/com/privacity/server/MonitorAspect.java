package com.privacity.server;

import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class MonitorAspect {
//    @Before(value = "@within(app.Monitor) || @annotation(app.Monitor)")
//    public void before(JoinPoint joinPoint) throws Throwable {
//       System.out.println("monitor.before, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName());
//    }
//
//    @After(value = "@within(app.Monitor) || @annotation(app.Monitor)")
//    public void after(JoinPoint joinPoint) throws Throwable {
//    	System.out.println("monitor.after, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName());
//    }
}