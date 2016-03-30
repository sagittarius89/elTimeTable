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

package pl.org.elzylab.eltimetable.beans.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.org.elzylab.eltimetable.tc.persistence.Database;

/**
 * ====================================================================
 * Description
 * ==================================================================== Class
 * contains static methods that do operations on database connected with User
 * table
 * 
 *
 */
public class Users {

	/**
	 * Method select user form database by given username and passowrd
	 * 
	 * @param username
	 * @param password
	 * @return requested user data transaction object or null if such user does
	 *         not exist
	 */
	public static UserDTO getUser(String username, String password) {
		final String SQL = "SELECT ID, USERNAME, NAME, SURNAME, PASSWORD "
						+ "FROM AD_USER WHERE USERNAME=? AND PASSWORD = ?";
		String md5 = Database.getMD5(password);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, username);
			ps.setString(2, md5);

			rs = ps.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurname(rs.getString(4));
				user.setPassword(rs.getString(5));

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

		return null;
	}

}
