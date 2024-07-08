package com.privacity;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.privacity.server.component.common.service.facade.FacadeComponent;

//@Configuration
//@EnableAspectJAutoProxy
//@Aspect
public class AppConfigurationMethodRolValidation {
    

	//@Pointcut("within(com.privacity.server.component..*)")
    public void monitor() { }
    
    @Bean
    public AppConfigurationMethodRolValidationInterceptor configurationMethodRolValidationInterceptor() {
        return new AppConfigurationMethodRolValidationInterceptor(true);
    }

    @Bean
    public Advisor configurationMethodRolValidationAdvisor(@Autowired  FacadeComponent comps) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
        		"within(com.privacity.server.component.gr2ucpo.GrupoValidationService..*) "
        		
        		+ " || within(com.privacity.server.compo2ncent.grupo.GrupoValidationService*) " 
        		);

        return new DefaultPointcutAdvisor(pointcut, new AppConfigurationMethodRolValidationInterceptor(comps));
    }
    
}