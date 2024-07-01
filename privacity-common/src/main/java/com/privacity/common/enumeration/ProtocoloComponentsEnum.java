package com.privacity.common.enumeration;

public enum ProtocoloComponentsEnum {

	PROTOCOLO_COMPONENT_REQUEST_ID("PROTOCOLO_COMPONENT_REQUEST_ID"),

	PROTOCOLO_COMPONENT_PRIVACITY_RSA("PROTOCOLO_COMPONENT_PRIVACITY_RSA"),

	PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE("PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE"),

	PROTOCOLO_COMPONENT_ENCRYPT_KEYS("PROTOCOLO_COMPONENT_ENCRYPT_KEYS"),

	PROTOCOLO_COMPONENT_AUTH("PROTOCOLO_COMPONENT_AUTH"),

	PROTOCOLO_COMPONENT_MESSAGE("PROTOCOLO_COMPONENT_MESSAGE"),

	PROTOCOLO_COMPONENT_MY_ACCOUNT("PROTOCOLO_COMPONENT_MY_ACCOUNT"),

	PROTOCOLO_COMPONENT_GRUPO("PROTOCOLO_COMPONENT_GRUPO"),
	
	PING_COMPONENT("PING_COMPONENT");



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
