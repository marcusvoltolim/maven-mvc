package com.apirest.mvc.exception;

public class EntityCreateException extends CustomRuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityCreateException(ValidationMsg validationMsg, Object... params) {
		super(validationMsg, params);
	}

}