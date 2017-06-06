package com.msrm.sqlrunner.exception;

import org.apache.log4j.Logger;

public class ExceptionUtil {

	public static void raiseApplicationException(Throwable t, Logger logger) throws SqlRunnerException {
		String message = "Failed due to " + t.getMessage();
		logger.error(message);
		throw new SqlRunnerException(message);
	}

	public static void error(Throwable t, Logger logger) {
		String message = "Failed due to " + t.getMessage();
		logger.error(message);
	}

}
