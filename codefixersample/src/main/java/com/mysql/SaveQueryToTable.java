package com.mysql;

/**
 * 
 * SaveQueryToTable
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveQueryToTable {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/mydatabase";
		String user = "root";
		String password = null;
		String query = "SELECT * FROM mytable";
		//String tableName = "newtable";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// Create new table
			stmt.executeUpdate("CREATE TABLE newtable LIKE mytable");
			// Insert data into new table
			String insertQuery = "INSERT INTO newtable VALUES (";
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				insertQuery += "?,";
			}
			insertQuery = insertQuery.substring(0, insertQuery.length() - 1) + ")";
			pstmt = conn.prepareStatement(insertQuery);
			while (rs.next()) {
				for (int i = 1; i <= numColumns; i++) {
					pstmt.setObject(i, rs.getObject(i));
				}
				pstmt.executeUpdate();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}

			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}

			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}

			}

		}
	}

}
