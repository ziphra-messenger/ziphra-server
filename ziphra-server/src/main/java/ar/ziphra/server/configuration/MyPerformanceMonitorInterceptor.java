package ar.ziphra.server.configuration;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

public class MyPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
	private static final int MAX_LOG = 500;
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
    			System.out.println("        " + args[i].getClass().getName() + " -> " + shrinkString(args[i].toString()));
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
	
	private String shrinkString(String s) {
		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", -" , "").replace(", " , "");
						
		return r.substring(0, (r.length()> MAX_LOG)? MAX_LOG : r.length()-1);
	}
}