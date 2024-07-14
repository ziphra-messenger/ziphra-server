package com.privacity.server.common.utils;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.server.common.adapters.LocalDateAdapter;

public class UtilsString {
	
	private static final int MAX_LOG = 2000;

	public static String shrinkString(String s) {
		
		
		if (s == null) return s;
		if (s.length() < MAX_LOG) return s;
		
		String r = s.toString().replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "")
				.replace("  ", " ").replace("  ", " ").replace("  ", " ").replace(", " , "");
						
		return r.substring(0, (r.length()> MAX_LOG)? MAX_LOG : r.length()-1);
	}
	
	public static String gsonToSend(Object s) {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create().toJson(s).replace("\n", "").replace("\t", "").replace("\r", "").replace("\b", "").replace("\f", "").replace(" ", "");
        
	}
	
	public static Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        
	}
}
