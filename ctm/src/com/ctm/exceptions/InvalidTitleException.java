package com.ctm.exceptions;

import com.ctm.exceptions.CTMException;

public class InvalidTitleException extends CTMException {

    private static final long serialVersionUID = 1L;

    public InvalidTitleException() {
	super();
    }

    public InvalidTitleException(String message, Throwable cause) {
	super(message, cause);
    }

    public InvalidTitleException(String message) {
	super(message);
    }

}
