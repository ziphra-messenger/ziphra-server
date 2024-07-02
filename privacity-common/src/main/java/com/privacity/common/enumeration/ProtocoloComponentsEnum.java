package com.privacity.common.enumeration;

public enum ProtocoloComponentsEnum {

	REQUEST_ID("REQUEST_ID"),

	PRIVACITY_RSA("PRIVACITY_RSA"),

	
	
	SERVER_CONF_UNSECURE("SERVER_CONF_UNSECURE"),

	ENCRYPT_KEYS("ENCRYPT_KEYS"),

	AUTH("AUTH"),

	MESSAGE("MESSAGE"),

	MY_ACCOUNT("MY_ACCOUNT"),

	GRUPO("GRUPO"),
	
	PING("PING");



	private final String name;       

	private ProtocoloComponentsEnum(String s) {
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
