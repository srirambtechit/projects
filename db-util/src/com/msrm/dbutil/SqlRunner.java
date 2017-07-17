package com.msrm.dbutil;

import java.util.HashMap;
import java.util.Map;

public class SqlRunner {

	private static Map<String, Boolean> executeQuery = new HashMap<>();

	public static SqlResult run(String user, String sql) {
		executeQuery.put(user, true);
		SqlTask sqlTask = new SqlTask(sql);
		SqlResult sqlResult = sqlTask.call();
		executeQuery.put(user, false);
		return sqlResult;
	}

}
