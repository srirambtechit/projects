package com.msrm.sqlrunner.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.beans.SqlResult;
import com.msrm.sqlrunner.beans.User;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;

public final class SqlRunner {

	private static Logger logger = Logger.getLogger(SqlRunner.class);

	// used to hold current execution of SQL and its user
	private static Map<String, String> registry;

	static {
		logger.info("Initializing SqlRunner");
		registry = new HashMap<>();
	}

	private SqlRunner() {
	}

	/**
	 * It registers SQL to the user
	 * 
	 * @param user
	 * @param sql
	 */
	public static void registerSQL(User user, String sql) {
		registry.put(user.getUsername(), sql);
	}

	/**
	 * It removes the SQL query against the user from the registry;
	 * 
	 * @param user
	 */
	public static void completeSQL(User user) {
		Objects.requireNonNull(user);
		registry.put(user.getUsername(), null);
	}

	/**
	 * sqlRunning performs the validation of SQL being executed by the user. It
	 * validates as True only when the user is not executing SQL at this point
	 * and time, otherwise returns false;
	 * 
	 * @param user
	 * @return
	 */
	public static boolean isSQLRunning(User user) {
		return Objects.nonNull(user) && registry.get(user.getUsername()) != null;
	}

	public static SqlResult run(User user, String sql) throws SqlRunnerException {
		System.out.println(registry);
		if (!isSQLRunning(user)) {
			registerSQL(user, sql);
			SqlResult sqlResult = SqlEngine.executeSQL(sql);
			completeSQL(user);
			return sqlResult;
		} else {
			String message = user.getUsername() + " is already executing a SQL; Please wait for " + sql;
			ExceptionUtil.raiseApplicationError(message, logger);
		}
		return null;
	}

	public static void main(String[] args) throws SqlRunnerException {
		User user1 = new User("sriram", "pass1234");
		String sql1 = "select * from product";

		User user2 = new User("surya", "xyz123");
		String sql2 = "select * from user_account";

		User user3 = new User("mani", "manimani");
		String sql3 = "select * from product, user_account";

		run(user1, sql1);
		run(user2, sql2);
		run(user1, sql1);
		run(user3, sql3);
	}

}
