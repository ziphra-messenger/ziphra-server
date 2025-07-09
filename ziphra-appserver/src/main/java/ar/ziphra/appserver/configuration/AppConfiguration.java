package ar.ziphra.appserver.configuration;

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
public class AppConfiguration {
    

	//@Pointcut("within(ar.ziphra.appserver.component..*)")
    public void monitor() { }
    
    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
        		"within(ar.ziphra.appserver.component.grupous2erconf..*) "
        		
        		+ " || within(ar.ziphra.appserver.component.g2rupo..*) " 
        		);

        return new DefaultPointcutAdvisor(pointcut, new MyPerformanceMonitorInterceptor());
    }
    
}