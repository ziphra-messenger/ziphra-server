package com.privacity.server.sessionmanager.util.protocolomap;

import lombok.Data;

@Data
public class ProtocoloValue {

	private Class<?>parametersType;
	public ProtocoloValue(Class<?> parametersType) {
		super();
		this.parametersType =parametersType;
	}

	public static ProtocoloValue build(Class<?> parametersType) {
		return new ProtocoloValue(parametersType);
	}

}
