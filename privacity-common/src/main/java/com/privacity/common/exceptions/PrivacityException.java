package com.privacity.common.exceptions;

import com.privacity.common.enumeration.ExceptionReturnCode;

public class PrivacityException extends Exception {

	private static final long serialVersionUID = -187506478356515347L;

	public PrivacityException(ExceptionReturnCode code) {
		super(code.getCode());
	}

	public PrivacityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrivacityException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrivacityException(ExceptionReturnCode code, Throwable cause) {
		super(code.getCode(), cause);
	}
	
	public PrivacityException(String message) {
		super(message);
	}

	public PrivacityException(Throwable cause) {
		super(cause);
	}
}
