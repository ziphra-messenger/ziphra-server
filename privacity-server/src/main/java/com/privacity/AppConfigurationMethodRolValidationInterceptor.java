package com.privacity;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.privacity.common.RolesAllowed;
import com.privacity.common.interfaces.GrupoRoleInterface;
import com.privacity.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;

public class AppConfigurationMethodRolValidationInterceptor extends AbstractMonitoringInterceptor {
    
	
	FacadeComponent comps;
	
    public AppConfigurationMethodRolValidationInterceptor(FacadeComponent comps) {
    	this.comps = comps;
    }

    public AppConfigurationMethodRolValidationInterceptor(boolean useDynamicLogger) {
            setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log) 
      throws Throwable {
        System.out.println("----->AppConfigurationMethodRolValidationInterceptor");
        
        RolesAllowed ann = invocation.getStaticPart().getAnnotation(RolesAllowed.class);
    
        if (ann == null ) {
        	return invocation.proceed();
        }
        
        Object[] args = invocation.getArguments();
        Grupo g=null;
        Usuario u = null;
        UserForGrupo ufg = null;
        
        for (int i=0; i < args.length ; i++) {
        	if (args[i] instanceof GrupoRoleInterface) {
        		g = ((GrupoRoleInterface)args[i]).getGrupo();
        		
				
			}
        	if (args[i] instanceof UsuarioRoleInterface) {
        		u = ((UsuarioRoleInterface)args[i]).getUsuarioLoggued();
        		
				
			}
        	
        	if (args[i] instanceof UserForGrupoRoleInterface) {
        		ufg = ((UserForGrupoRoleInterface)args[i]).getUserForGrupo();
				
			}
        }
        
        

        
        boolean isOk= false;
        
        for (int i=0; i < ann.value().length ; i++) {
        	if (ann.value()[i].equals(ufg.getRole())){
        		isOk=true;
        		break;
        	}
        }
        if (isOk)  return invocation.proceed();
        

        throw new Exception("Sin permisos");    



            

    }

	private Object showReturn(Object proceed) {
		// TODO Auto-generated method stub
		return proceed;
	}
}