package com.fpt.g52.carsharing.booking.domain.exceptions;


public class ResourceInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public ResourceInvalidException() {
		super();
	}

	public ResourceInvalidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		// TODO Auto-generated constructor stub
	}

	public ResourceInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceInvalidException(String message) {
		super(message);
	}

	public ResourceInvalidException(Throwable cause) {
		super(cause);
	}

	
	
}
