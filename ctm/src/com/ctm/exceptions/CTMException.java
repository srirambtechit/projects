package com.ctm.exceptions;

public class CTMException extends Exception {

    private static final long serialVersionUID = 1L;

    public CTMException() {
	super();
    }

    public CTMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public CTMException(String message, Throwable cause) {
	super(message, cause);
    }

    public CTMException(String message) {
	super(message);
    }

    public CTMException(Throwable cause) {
	super(cause);
    }

}
