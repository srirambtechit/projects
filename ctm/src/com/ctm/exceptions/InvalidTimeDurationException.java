package com.ctm.exceptions;

import com.ctm.exceptions.CTMException;

public class InvalidTimeDurationException extends CTMException {

    private static final long serialVersionUID = 1L;

    public InvalidTimeDurationException() {
	super();
    }

    public InvalidTimeDurationException(String message, Throwable cause) {
	super(message, cause);
    }

    public InvalidTimeDurationException(String message) {
	super(message);
    }

}
