package com.privacity.sessionmanager.util.protocolomap;

import lombok.Data;

@Data
public class ProtocoloValue {

	private Class<?>parametersType;
	private Class<?>parametersTypeOut;
	public ProtocoloValue(Class<?> parametersType,Class<?> parametersTypeOut) {
		super();
		this.parametersType =parametersType;
		this.parametersTypeOut =parametersTypeOut;
	}

	public static ProtocoloValue build(Class<?> parametersType, Class<?> parametersTypeOut) {
		return new ProtocoloValue(parametersType, parametersTypeOut);
	}

}
