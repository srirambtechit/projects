package com.msrm.dbutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SqlTask {

	private String sql;

	public SqlTask(String sql) {
		this.sql = sql;
	}

	public SqlResult call() {
		System.out.println("Query is running on DB");
		System.out.println("Query: " + sql);
		SqlResult sqlResult = new SqlResult();
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/mysqldb");

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
				e.printStackTrace();
			}
		} catch (NamingException e) {
			System.out.println("Error at naming");
			e.printStackTrace();
		}
		return sqlResult;
	}

}