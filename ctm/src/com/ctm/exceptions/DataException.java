package com.ctm.exceptions;

public class DataException extends CTMException {

    private static final long serialVersionUID = 1L;

    public DataException() {
	super();
    }

    public DataException(String message) {
	super(message);
    }

    public DataException(String message, Throwable cause) {
	super(message, cause);
    }

}