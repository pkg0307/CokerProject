package com.mycommunity.common.exception;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException() {
		super();
	}
	public AuthorizationException(String message) {
		super(message);
	}
}
