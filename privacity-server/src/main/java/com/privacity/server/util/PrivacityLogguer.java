package com.privacity.server.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class PrivacityLogguer {

	public void write( Object o) {
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
//                .create();
//        
//        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
//        StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
//        String methodName = e.getMethodName();
//
//        String su = "error";
//		try {
//			su = gson.toJson(o).substring(1, 500);;
//		} catch (Exception e1) {
//			try {
//				//su = gson.toJson(o);
//			} catch (Exception e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//		}
//        String separator = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//        System.out.println(separator);
//        System.out.println(separator);
//        System.out.println(separator);
//        System.out.println(e.getClassName());
//        System.out.println(methodName);
//
//        System.out.println(su);
//        System.out.println(separator);
//        System.out.println(separator);
//        System.out.println(separator);
        
		
	}
}
