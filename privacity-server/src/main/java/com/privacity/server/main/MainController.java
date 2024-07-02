package com.privacity.server.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.privacity.server.common.enumeration.Urls;



@RestController
@RequestMapping(path = "/entry")
public class MainController {
	
	

	   
	/*
	public static final String CONSTANT_URL_PATH_PRIVATE_DOWNLOAD_DATA = "/private/download/data";
	public static final String CONSTANT_URL_PATH_PRIVATE = "/private/entry";
	public static final String CONSTANT_URL_PATH_PRIVATE_SEND = "/private/send";
	public static final String CONSTANT_URL_PATH_PUBLIC = "/public/entry";
	public static final String CONSTANT_URL_PATH_FREE = "/free/entry";
	*/
		
		
	@PostMapping("/{controller}")
	
	public ModelAndView in2(@PathVariable String controller) {
		
			
		try {
			return new ModelAndView("forward:"+Enum.valueOf(Urls.class, controller));
		} catch (Exception e) {
			
			return new ModelAndView("forward:"+Urls.CONSTANT_URL_PATH_ERROR);
		}

	}
	
	public static void main(String[] args) {
		System.out.println(Urls.CONSTANT_URL_PATH_FREE.name());
	}
}
