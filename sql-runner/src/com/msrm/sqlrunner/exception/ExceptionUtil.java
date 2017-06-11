package com.msrm.sqlrunner.exception;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.util.JsonUtil;

public class ExceptionUtil {

	public static void error(Throwable t, Logger logger) {
		logger.error("Failed due to " + t.getMessage());
	}

	public static void raiseApplicationError(String message, Logger logger) throws SqlRunnerException {
		logger.error("Failed due to " + message);
		//@formatter:off
		String resultJson = JsonUtil.Builder.newBuilder()
				.property("status", "error")
				.property("type", "Application Error")
				.property("error", message)
				.build();
		//@formatter:on
		throw new SqlRunnerException(resultJson);
	}

	public static void raiseApplicationException(Throwable t, Logger logger) throws SqlRunnerException {
		logger.error("Failed due to " + t.getMessage());
		//@formatter:off
		String resultJson = JsonUtil.Builder.newBuilder()
				.property("status", "error")
				.property("type", "Application Error")
				.property("cause", (t.getCause() != null) ? t.getCause().getMessage() : "")
				.property("error", t.getMessage())
				.build();
		//@formatter:on
		throw new SqlRunnerException(resultJson);
	}

	public static String raiseSQLException(SQLException e, Logger logger) throws SqlRunnerException {
		logger.error("Failed due to " + e.getMessage());
		//@formatter:off
		String resultJson = JsonUtil.Builder.newBuilder()
				.property("status", "error")
				.property("type", "SQL Error")
				.property("cause", (e.getCause() != null) ? e.getCause().getMessage() : "")
				.property("error", e.getMessage())
				.build();
		//@formatter:on
		return resultJson;
	}

	public static String raiseNamingException(NamingException e, Logger logger) throws SqlRunnerException {
		logger.error("Failed due to " + e.getMessage());
		//@formatter:off
		String resultJson = JsonUtil.Builder.newBuilder()
				.property("status", "error")
				.property("type", "Naming Error")
				.property("cause", (e.getCause() != null) ? e.getCause().getMessage() : "")
				.property("error", e.getMessage())
				.build();
		//@formatter:on
		return resultJson;
	}

}
