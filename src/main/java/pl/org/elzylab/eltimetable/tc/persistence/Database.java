/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) ${date} elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */

package pl.org.elzylab.eltimetable.tc.persistence;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * ====================================================================
 * Description
 * ====================================================================
 *
 * Util - like class that keeps most of code connected with database services
 * 
 *
 */
public class Database {

	/** JDBC driver @TODO move it to properties */
	private static final String JDBC_DRIVER = "org.postgresql.Driver";

	/** JDBC connection address @TODO move it to properites */
	private static final String DB_URL =
		"jdbc:postgresql://localhost:5432/eltimetable";

	/** JDBC username @TODO move it to properites */
	private static final String USER = "postgres";

	/** JDBC password @TODO move it to properites */
	private static final String PASS = "a4tech";

	/** JDBC database connection */
	private static Connection mConnection = null;

	/**
	 * Method returns active connection to database (or if it is not possible it
	 * throws exception
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (mConnection == null || mConnection.isClosed()) {
			initConnection();
		}

		return mConnection;
	}

	/**
	 * Initializes connection to database (not necessary to use outside,
	 * connection will be auto initiated from level of {@link #getConnection()}
	 * method when it will be called first time).
	 */
	public static void initConnection() {
		try {

			Class.forName(JDBC_DRIVER);
			mConnection = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method converts string to it's md5 check-sum
	 * 
	 * @param string
	 *            data to convert
	 * @return given data checksum
	 */
	public static String getMD5(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(string.getBytes(Charset.forName("UTF-8")));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
					.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * Closes given connection and associated given objects
	 * 
	 * @param con
	 *            connection to close
	 * @param ps
	 *            statement associated with connection
	 * @param rs
	 *            result set associated with statement and connection
	 * 
	 */
	public static void closeConnection(Connection con, PreparedStatement ps,
		ResultSet rs) {
		if (rs != null)
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		;

		if (ps != null)
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
		;

		if (con != null)
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		;
	}

	/**
	 * Sets integer value, or if value is null then null to given position of
	 * prepared statement by {@link PreparedStatement#setNull(int, int)} method.
	 * 
	 * @param ps
	 *            prepared statement
	 * @param order
	 *            position of value in statement
	 * @param value
	 *            value to set
	 * @throws SQLException
	 */
	public static void setIntOrNull(PreparedStatement ps, Integer order,
		Integer value) throws SQLException {
		if (value == null) {
			ps.setNull(order, Types.INTEGER);
		} else {
			ps.setInt(order, value);
		}
	}

	/**
	 * Sets string value, or if value is null then null to given position of
	 * prepared statement by {@link PreparedStatement#setNull(int, string)}
	 * method.
	 * 
	 * @param ps
	 *            prepared statement
	 * @param order
	 *            position of value in statement
	 * @param value
	 *            value to set
	 * @throws SQLException
	 */
	public static void setStringOrNull(PreparedStatement ps, Integer order,
		String value) throws SQLException {
		if (value == null) {
			ps.setNull(order, Types.VARCHAR);
		} else {
			ps.setString(order, value);
		}
	}

	/**
	 * Sets timestamp value, or if value is null then null to given position of
	 * prepared statement by {@link PreparedStatement#setNull(int, timestamp)}
	 * method.
	 * 
	 * @param ps
	 *            prepared statement
	 * @param order
	 *            position of value in statement
	 * @param value
	 *            value to set
	 * @throws SQLException
	 */
	public static void setTimestampOrNull(PreparedStatement ps, Integer order,
		String value) throws SQLException {
		if (value == null) {
			ps.setNull(order, Types.TIMESTAMP);
		} else {
			ps.setString(order, value);
		}
	}
	
	/**
	 * Converts list of integers to SQL-understandable parameter for IN operator
	 * @param list
	 * @return
	 */
	public static String toString(List<Integer> list) {
		StringBuffer buff = new StringBuffer();
		
		for(int i=0; i<list.size(); ++i)
		{
			buff.append(list.get(i));
			
			if(i + 1 < list.size())
				buff.append(",");
		}
		
		return buff.toString();
	}
}
