package com.privacity;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {
    

	//@Pointcut("within(com.privacity.server.component..*)")
    public void monitor() { }
    
    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
        		"within(com.privacity.server.component.grupouserconf..*) "
        		
        		+ " || within(com.privacity.server.component.grupo..*) " 
        		);

        return new DefaultPointcutAdvisor(pointcut, new MyPerformanceMonitorInterceptor());
    }
    
}