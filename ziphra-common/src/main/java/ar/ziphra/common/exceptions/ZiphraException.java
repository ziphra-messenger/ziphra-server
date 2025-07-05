package ar.ziphra.common.exceptions;

import ar.ziphra.common.enumeration.ExceptionReturnCode;

public class ZiphraException extends Exception {

	private static final long serialVersionUID = -187506478356515347L;

	public ZiphraException(ExceptionReturnCode code) {
		super(code.getCode());
	}

	public ZiphraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ZiphraException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZiphraException(ExceptionReturnCode code, Throwable cause) {
		super(code.getCode(), cause);
	}
	
	public ZiphraException(String message) {
		super(message);
	}

	public ZiphraException(Throwable cause) {
		super(cause);
	}
}
