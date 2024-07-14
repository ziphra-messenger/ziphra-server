package com.privacity.server.exceptions;

import com.privacity.common.enumeration.ExceptionReturnCode;

import lombok.Getter;

public class ValidationException extends PrivacityException {

	@Getter
	private String description;
	public ValidationException(ExceptionReturnCode userUserNotLogger) {
		super(userUserNotLogger.getCode());
		
		description = userUserNotLogger.getDescription();
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ValidationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
