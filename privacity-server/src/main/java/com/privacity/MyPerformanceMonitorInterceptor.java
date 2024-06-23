package com.privacity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.privacity.common.annotations.ExcludeInterceptorLog;
import com.privacity.server.util.LocalDateAdapter;

public class MyPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
    
    public MyPerformanceMonitorInterceptor() {
    }

    public MyPerformanceMonitorInterceptor(boolean useDynamicLogger) {
            setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log) 
      throws Throwable {
    	String name = createInvocationTraceName(invocation);
    	Object[] args = invocation.getArguments();
    	
    	if (args.length == 0) {
    		 return showReturn (invocation.proceed());
    	}
    	
    	if (name.contains("convertIdGrupoStringToLong")){
    			return showReturn (invocation.proceed());
    		}else {
        
        long start = System.currentTimeMillis();
      
        System.out.println("-----> Method " + name);

    	

    	for ( int i = 0 ; i < args.length ; i++ ) {
    		if (args[i] != null) {
    			System.out.println("        " + args[i].getClass().getName() + " -> " + args[i].toString());
    		}

    	}
        try {	
        	return showReturn (invocation.proceed());
        }
        finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time > 1000){
            	System.out.println("      1000 ms!");
            }            

            System.out.println("-----> Method "+name+" execution lasted:"+time+" ms");
            
        }
    	}
    }

	private Object showReturn(Object proceed) {
		// TODO Auto-generated method stub
		return proceed;
	}
}