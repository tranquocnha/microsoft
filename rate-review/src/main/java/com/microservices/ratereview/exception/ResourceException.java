package com.microservices.ratereview.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceException(String message) {
		super(message);
	}
}
