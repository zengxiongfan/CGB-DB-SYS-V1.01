package com.cy.pj.common.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 5843835376260549700L;
	public ServiceException() {
		super();
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}

}
