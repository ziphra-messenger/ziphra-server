package com.privacity.commonback.common.enumeration;

   public enum Urls {
		CONSTANT_URL_PATH_PRIVATE_DOWNLOAD_DATA("/private/download/data"),
		CONSTANT_URL_PATH_PRIVATE ("/private/entry"),
		CONSTANT_URL_PATH_PRIVATE_SEND ("/private/send"),
		CONSTANT_URL_PATH_PUBLIC ("/public/entry"),
		CONSTANT_URL_PATH_FREE ("/free/entry"),
	    CONSTANT_URL_PATH_ERROR ("/doc/en/error/error.html"),
	   CONSTANT_URL_PATH_PRIVATE_WS ("WebSocket");
	   
	    private final String name;       

	    private Urls(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName) {
	        // (otherName == null) check is not needed because name.equals(null) returns false 
	        return name.equals(otherName);
	    }

	    public String toString() {
	       return this.name;
	    }
   }