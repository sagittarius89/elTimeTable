/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) 17 mar 2016 elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */
package pl.org.elzylab.eltimetable.beans.tabs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.org.elzylab.eltimetable.tc.persistence.Database;

/**
 * ====================================================================
 * Description
 * ==================================================================== Class
 * which contains all static methods to grab data from <b>TT_TAB</b> table from
 * database.
 *
 */
public class Tabs {

	/**
	 * Selects tabs of logged user from database and returns as data transaction
	 * objects list
	 * 
	 * @param userId
	 *            user's id
	 * @return list of data transaction object
	 */
	public static List<TabDTO> getTabsForUser(Integer userId) {
		final String SQL =
			"SELECT ID, NAME, TORDER, IS_DEFAULT "
				+ "FROM TT_TAB WHERE OWNER = ? ORDER BY TORDER";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TabDTO> result = new ArrayList<>();

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, userId);

			rs = ps.executeQuery();

			while (rs.next()) {
				TabDTO tab = new TabDTO();

				tab.setId(String.valueOf(rs.getInt(1)));
				tab.setName(rs.getString(2));
				tab.setOrder(rs.getInt(3));
				tab.setDefault(rs.getBoolean(4));

				result.add(tab);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

		return result;
	}

	/**
	 * Check if there exist any task for given tab
	 * 
	 * @param tabId
	 *            tab id
	 * @param userId
	 *            current user id
	 * @return boolean value that determinates if there is any active task for
	 *         given tab
	 */
	public static Boolean checkIfTabCanBeRemoved(Integer tabId, Integer userId) {
		final String SQL =
			"SELECT COUNT(*) FROM TT_TASK " + "INNER JOIN TT_TAB"
				+ " ON TT_TAB.ID=TT_TASK.TAB_ID "
				+ "WHERE TT_TAB.ID=? AND TT_TAB.OWNER=? AND TT_TASK.STATUS=1";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, tabId);
			ps.setInt(2, userId);

			rs = ps.executeQuery();

			if (rs.next() & rs.getInt(1) > 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

		return true;
	}

	/**
	 * @param newTabs
	 * @param id
	 */
	public static void createNewTabs(List<TabDTO> newTabs, Integer userId) {
		if (newTabs == null || newTabs.isEmpty())
			return;

		final String SQL =
			"INSERT INTO TT_TAB (NAME, TORDER, OWNER, IS_DEFAULT) "
				+ "VALUES (?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);

			for (TabDTO tab : newTabs) {
				ps.setString(1, tab.getName());
				ps.setInt(2, tab.getOrder());
				ps.setInt(3, userId);
				ps.setBoolean(4, tab.isDefault());

				ps.addBatch();
			}

			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, null);
		}
	}

	/**
	 * @param tabsToUpdate
	 * @param id
	 */
	public static void updateTabs(List<TabDTO> tabsToUpdate, Integer userId) {
		if (tabsToUpdate == null || tabsToUpdate.isEmpty())
			return;

		final String SQL =
			"UPDATE TT_TAB SET NAME=?, TORDER=?, IS_DEFAULT=? "
				+ "WHERE ID=? AND OWNER=?";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);

			for (TabDTO tab : tabsToUpdate) {
				ps.setString(1, tab.getName());
				ps.setInt(2, tab.getOrder());
				ps.setBoolean(3, tab.isDefault());
				ps.setInt(4, Integer.valueOf(tab.getId()));
				ps.setInt(5, userId);

				ps.addBatch();
			}

			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, null);
		}

	}

	/**
	 * @param tabsToDelete
	 * @param id
	 */
	public static void deleteTabs(List<Integer> tabsToDelete, Integer userId) {
		if (tabsToDelete == null || tabsToDelete.isEmpty())
			return;
		final String tabsList = Database.toString(tabsToDelete);
		final String SQL =
			"DELETE FROM TT_TAB WHERE ID IN (" + tabsList + ") AND OWNER=?";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = Database.getConnection();

			setTasksTabToNull(con, tabsList);

			ps = con.prepareStatement(SQL);
			ps.setInt(1, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, null);
		}
	}

	/**
	 * Selects all inactive tasks assigned to given tab, and switch their
	 * assignment to null. In other words, this method moves all inactive task
	 * to "ALL" tab.
	 * 
	 * @param con
	 *            Database connection
	 * @param tabsList
	 *            well formed SQL TT_TAB ids separated by coma
	 * @throws SQLException
	 */
	private static void setTasksTabToNull(Connection con, String tabsList)
		throws SQLException {
		final String SQL =
			"UPDATE TT_TASK SET TAB_ID=NULL " + "WHERE TAB_ID IN (" + tabsList
				+ ") AND STATUS <> 1";

		PreparedStatement ps = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);
			ps.executeUpdate();

		} finally {
			Database.closeConnection(null, ps, null);
		}
	}
}
