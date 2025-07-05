package com.privacity.common.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class Validations {
	public static boolean isValidURL(String url) {
	    try {
	        new URL(url).toURI();
	        return true;
	    } catch (MalformedURLException e) {
	        return false;
	    } catch (URISyntaxException e) {
	        return false;
	    }
	}
}
