package com.msrm.sqlrunner.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.beans.SqlResult;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;

public class SqlEngine {

	private static Logger logger = Logger.getLogger(SqlEngine.class);

	private static ExecutorService workerThreads;

	static {
		workerThreads = Executors.newWorkStealingPool();
	}

	public static SqlResult executeSQL(String sql) throws SqlRunnerException {
		logger.info("Executing SQL : " + sql);
		System.out.println("Executing SQL : " + sql);
		Task task = new Task(sql);
		Future<SqlResult> future = workerThreads.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("ExecutorService Exception");
			throw new SqlRunnerException(e.getMessage());
		}
	}

	private static class Task implements Callable<SqlResult> {

		private String sql;

		private Task(String sql) {
			this.sql = sql;
		}

		@Override
		public SqlResult call() throws SqlRunnerException {
			System.out.println("Query is running on DB");
			SqlResult sqlResult = new SqlResult();
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource ds = (DataSource) envCtx.lookup("jdbc/niit");

				try (
						// Connection con =
						// DriverManager.getConnection(Configs.get(Configs.DB_JDBCURL),
						// Configs.get(Configs.DB_USERNAME),
						// Configs.get(Configs.DB_PASSWORD));
						Connection con = ds.getConnection();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery(sql);) {

					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						sqlResult.getColumns().add(metaData.getColumnName(i));
					}
					while (rs.next()) {
						List<String> row = new ArrayList<>();
						for (int i = 1; i <= columnCount; i++) {
							row.add(rs.getString(i));
						}
						sqlResult.getRows().add(row);
					}

					// try {
					// Thread.sleep(10000);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }

					System.out.println("sqlResult : " + sqlResult);
				} catch (SQLException e) {
					System.out.println("Error at threading");
					try {
						sqlResult.setErrorJson(ExceptionUtil.raiseSQLException(e, logger));
					} catch (Throwable t) {
						System.out.println("Exception captured and supressed!!!");
					}
				}
			} catch (NamingException e) {
				System.out.println("Error at naming");
				sqlResult.setErrorJson(ExceptionUtil.raiseNamingException(e, logger));
			}
			return sqlResult;
		}

	}

}
