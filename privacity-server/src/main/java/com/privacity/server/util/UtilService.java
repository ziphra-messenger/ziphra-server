package com.privacity.server.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.common.util.RandomGenerator;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.security.Usuario;

@Service
public class UtilService {

	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	private Gson gson;
	
	public UtilService() {
		super();
		 gson = new GsonBuilder()
	                .setPrettyPrinting()
	                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
	                .create();
	}
	
	public Object clon(Class clazz, Object o ) {
		
	       
        String j = gson.toJson(o);
        
        Object fromJson = gson.fromJson(j, clazz);
		return fromJson;
		
	}

	public Usuario getUser() {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    
		Usuario u = comps.repo().user().findByUsername(userDetail.getUsername()).get();
		return u;
	}    
 
	public String mix(String s,boolean mixIterationRandom, int mixIteration, int minIRamdomterationm , int maxIRamdomteration ) {
		
		int iteration = mixIteration;
		if (mixIterationRandom) {
			iteration = Integer.parseInt(RandomGenerator.generate(RandomGeneratorType.NUMERIC, minIRamdomterationm, maxIRamdomteration));
		}
	 for (int j = 0 ; j< iteration ; j++) {
	    	
	        String a="";
	        String b="";
	        char[] arr = s.toCharArray();

	    for (int i = 0 ; i <  arr.length ; i++) {
	    	
	    	
	    	if (i%2 == 0) {
	    		a= a + arr[i];
	    		
	    	}else {
	    		b= b + arr[i];
	    	}
	    	
	    	s = a+b;

	  }
	 }
	 return s; 
	}

    public  String shuffleString(String input) { 
        
        // Convert String to a list of Characters 
        List<Character> characters = new ArrayList<>(); 
        for (char c : input.toCharArray()) { 
            characters.add(c); 
        } 
  
        // Shuffle the list 
        Collections.shuffle(characters); 
  
        // Convert the list back to String 
        StringBuilder shuffledString = new StringBuilder(); 
        for (char c : characters) { 
            shuffledString.append(c); 
        } 
  
        return shuffledString.toString(); 
    } 
    
	public String unmix(String c,boolean mixIterationRandom, int mixIteration, int minIRamdomterationm , int maxIRamdomteration ) {
		
		int iteration = mixIteration;
		if (mixIterationRandom) {
			iteration = Integer.parseInt(RandomGenerator.generate(RandomGeneratorType.NUMERIC, minIRamdomterationm, maxIRamdomteration));
		}

		
    int j;
    for (j = 0 ; j <  400; j++) {
    	
        String a="";
        String b="";
        char[] arr = c.toCharArray();

        for (int i = 0 ; i <  (arr.length/2) ; i++) {
        	
        	
    		a= a + arr[i];
    		try {
				a= a + arr[i +arr.length/2];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
    	
    	c = a;
    }
    	
    	
    
   

 	   
    } 
    return c;
  }
}
