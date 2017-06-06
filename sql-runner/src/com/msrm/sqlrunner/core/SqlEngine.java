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

public class SqlEngine {

	private static Logger logger = Logger.getLogger(SqlEngine.class);

	private static ExecutorService workerThreads;

	static {
		workerThreads = Executors.newWorkStealingPool();
	}

	public static SqlResult executeSQL(String sql) {
		logger.info("Executing SQL : " + sql);
		Task task = new Task(sql);
		Future<SqlResult> future = workerThreads.submit(task);
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class Task implements Callable<SqlResult> {

		private String sql;

		private Task(String sql) {
			this.sql = sql;
		}

		@Override
		public SqlResult call() throws Exception {
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

					SqlResult sqlResult = new SqlResult();
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
					return sqlResult;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
