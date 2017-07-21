package com.msrm.jdk7.features;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MultiCatch {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// try-with-resource block to leave JVM to handle resource closing
			try (Connection con = DriverManager.getConnection("url", "user", "password");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from table");) {
				while (rs.next()) {
					System.out.println(rs.getString(1));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
