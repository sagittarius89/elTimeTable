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
package pl.org.elzylab.eltimetable.beans.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.persistence.Database;
import pl.org.elzylab.eltimetable.tc.persistence.SortDirection;

/**
 * ====================================================================
 * Description
 * ==================================================================== Class
 * which contains all static methods to grab data from <b>TT_TASK</b> table from
 * database.
 */
public class Tasks {

	/**
	 * Select task from databse by given user id and order.
	 * 
	 * @param userId
	 *            id of user from <b>AD_USER</b> table.
	 * @param orderBy
	 *            SQL understandable ORDER BY section. Use for sorting data.
	 * @param direction 
	 * @param tabId
	 *            Id of tab (TT_TAB) form which we request tasks, may be also
	 *            string such as ALL, ARCHIVED, DELETED
	 * 
	 * @return List of tasks packed into
	 *         {@link pl.org.elzylab.eltimetable.TaskDTO} type.
	 */
	public static List<TaskDTO> getTasks(Integer userId, String orderBy,
		SortDirection direction, String tabId) {

		StringBuilder whereCaluse = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		params.add(userId);

		whereCaluse.append("USER_ID=? AND STATUS IN (");

		if ("ARCHIVED".equals(tabId)) {
			whereCaluse.append(TaskStatus.ARCHIVED.getValue());
		} else if ("DELETED".equals(tabId)) {
			whereCaluse.append(TaskStatus.CANCELED.getValue());
		} else {
			whereCaluse.append(TaskStatus.CREATED.getValue());
			whereCaluse.append(", ");
			whereCaluse.append(TaskStatus.DONE.getValue());
		}

		whereCaluse.append(")");

		if (Utils.isInteger(tabId)) {
			whereCaluse.append(" AND TAB_ID=?");
			params.add(Integer.parseInt(tabId));
		}
		
		orderBy += " " + direction.toString() + " ";
		
		return getTasks(orderBy, whereCaluse.toString(), params.toArray());
	}

	/**
	 * Create task in database table <b>TT_TASK</b> with data given in
	 * {@link pl.org.elzylab.eltimetable.TaskDTO} object. Also new row in
	 * <b>TT_TASK</b> is mapped for user with given id.
	 * 
	 * @param userId
	 *            task owner
	 * @param task
	 *            data transaction object
	 */
	public static void createTask(Integer userId, TaskDTO task) {

		final String SQL =
			"INSERT INTO TT_TASK (NAME, DESCRIPTION, "
				+ "EXECUTION_TIME, DURATION, STATUS, "
				+ "COLOR, PROGRESS, EXP, NOTES, USER_ID, PRIOR, "
				+ "CREATION_TIME, TAB_ID) "
				+ "VALUES (?, ?, ?, ?, ?, ? ,0 , ?, ?, ?, ?, Now(), ?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Database.getConnection();
			ps =
				con.prepareStatement(SQL,
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, task.getName());
			ps.setString(2, task.getDescription());
			ps.setTimestamp(3, task.getTime());
			ps.setLong(4, task.getDuration());
			ps.setInt(5, task.getStatus());
			ps.setString(6, task.getColor());
			ps.setInt(7, task.getExp());
			ps.setString(8, task.getNotes());
			ps.setInt(9, userId);
			ps.setInt(10, task.getPriority());
			ps.setInt(11, task.getTabId());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				task.setId(rs.getInt(1));
			}

			Database.closeConnection(null, ps, rs);

			if (task.getParents() != null)
				createTaskRelations(con, task.getId(), task.getParents());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

	}

	/**
	 * Update task in <b>TT_TASK</b> table with data and unique key stored in
	 * {@link pl.org.elzylab.eltimetable.TaskDTO} object.
	 * 
	 * @param task
	 *            data transaction object with stored data to update
	 */
	public static void updateTask(Integer userId, TaskDTO task) {

		final String SQL =
			"UPDATE TT_TASK SET NAME=?, DESCRIPTION=?, "
				+ "EXECUTION_TIME=?, DURATION=?, STATUS=?, "
				+ "COLOR=?, PROGRESS=?, EXP=?, NOTES=?, PRIOR=?, TAB_ID=? "
				+ "WHERE ID=? AND USER_ID=?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);

			ps.setString(1, task.getName());
			ps.setString(2, task.getDescription());
			ps.setTimestamp(3, task.getTime());
			ps.setLong(4, task.getDuration());
			ps.setInt(5, task.getStatus());
			ps.setString(6, task.getColor());
			ps.setInt(7, task.getProgress());
			ps.setInt(8, task.getExp());
			ps.setString(9, task.getNotes());
			ps.setInt(10, task.getPriority());
			Database.setIntOrNull(ps, 11, task.getTabId());
			ps.setInt(12, task.getId());
			ps.setInt(13, userId);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

	}

	/**
	 * Put in <b>TT_PARENT_TASKS</b> table row with information about tasks
	 * relation.
	 * 
	 * @param con
	 *            database connection
	 * @see {@link pl.org.elzylab.eltimetable.tc.persistence.Database}
	 * @param childId
	 *            child-task unique key
	 * @param parentIds
	 *            parent-task unique key
	 */
	public static void createTaskRelations(Connection con, Integer childId,
		List<Integer> parentIds) {
		final String SQL =
			"INSERT INTO TT_PARENT_TASKS (PARENT_ID, CHILD_ID) "
				+ "VALUES (?, ?)";

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(SQL);

			for (Integer id : parentIds) {
				ps.setInt(1, id);
				ps.setInt(2, childId);

				ps.addBatch();
			}

			ps.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(null, ps, null);
		}
	}

	/**
	 * Returns list of specified and sorted task collection from <b>TT_TASK</b>
	 * table.
	 * 
	 * @param orderBy
	 *            SQL understandable part of query describing task order
	 * @param where
	 *            SQL/JDBC understandable clause describing selection. Can be
	 *            parameterized by ? signs. Parameters can be passed in values
	 *            table
	 * @param values
	 *            array with parameters for where clause.
	 * @return specified list of task converted to
	 *         {@link pl.org.elzylab.eltimetable.TaskDTO}
	 */
	private static List<TaskDTO> getTasks(String orderBy, String where,
		Object[] values) {
		final String SQL =
			"SELECT ID, NAME, DESCRIPTION, EXECUTION_TIME, "
				+ "DURATION, STATUS, COLOR, PROGRESS, EXP, NOTES, "
				+ "PRIOR, TAB_ID, CREATION_TIME FROM TT_TASK WHERE " + where
				+ " ORDER BY STATUS, " + orderBy
				+ ", STATUS, CREATION_TIME, PROGRESS, EXP";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TaskDTO> result = new ArrayList<TaskDTO>();

		try {
			con = Database.getConnection();
			ps = con.prepareStatement(SQL);

			for (int i = 0; i < values.length; ++i) {
				if (values[i].getClass().equals(Integer.class)) {
					ps.setInt(i + 1, (Integer) values[i]);
				} else if (values[i].getClass().equals(String.class)) {
					ps.setString(i + 1, (String) values[i]);
				} else if (values[i].getClass().equals(Timestamp.class)) {
					ps.setTimestamp(i + 1, (Timestamp) values[i]);
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				TaskDTO task = new TaskDTO();

				task.setId(rs.getInt(1));
				task.setName(rs.getString(2));
				task.setDescription(rs.getString(3));
				task.setTime(rs.getTimestamp(4));
				task.setDuration(rs.getLong(5));
				task.setStatus(rs.getInt(6));
				task.setColor(rs.getString(7));
				task.setProgress(rs.getInt(8));
				task.setExp(rs.getInt(9));
				task.setNotes(rs.getString(10));
				task.setPriority(rs.getInt(11));
				task.setTabId(rs.getInt(12));
				task.setCreationTime(rs.getTimestamp(13));

				result.add(task);
			}

			Database.closeConnection(null, ps, rs);

			for (TaskDTO task : result)
				task.setParents(loadParentTask(con, task.getId()));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(con, ps, rs);
		}

		return result;
	}

	/**
	 * Method loads list of parent task for given child-task unique key
	 * 
	 * @param con
	 *            database connection
	 * @see {@link pl.org.elzylab.eltimetable.tc.persistence.Database}
	 * @param taskId
	 *            child unique key
	 * @return list of parent ids
	 */
	private static List<Integer> loadParentTask(Connection con, Integer taskId) {

		final String SQL =
			"SELECT PARENT_ID FROM TT_PARENT_TASKS " + "WHERE CHILD_ID=?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> result = null;

		try {
			ps = con.prepareStatement(SQL);
			ps.setInt(1, taskId);

			rs = ps.executeQuery();

			while (rs.next()) {
				if (result == null)
					result = new ArrayList<>();

				result.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(null, ps, rs);
		}

		return result;
	}

	/**
	 * Changes status of task with given unique key in <b>TT_TASK</b> table
	 * 
	 * @param id
	 *            task uniqu key
	 * @param status
	 *            new status to setup
	 * @param userId
	 * 			  owner id
	 */
	public static void changeStatus(Integer id, TaskStatus status,
		Integer userId) {
		final String SQL =
			"UPDATE TT_TASK SET STATUS=?, DONE_TIME=NOW()  "
				+ "WHERE ID = ? AND USER_ID=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, status.getValue());
			ps.setInt(2, id);
			ps.setInt(3, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn, ps, null);
		}
	}

	/**
	 * Method used for clean all task marked as done, and move to archive. In
	 * other words to change {@link #TaskStatus} form DONE to ARCHIVED.
	 */
	public static void cleanupStatuses() {
		final String SQL =
			"UPDATE TT_TASK SET STATUS=" + TaskStatus.ARCHIVED.getValue()
				+ " WHERE DONE_TIME < NOW() "
				+ "- interval '1 day' AND STATUS=" + TaskStatus.DONE.getValue();

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn, ps, null);
		}
	}

	/**
	 * Updates progress of task with given unique key with new value.
	 * 
	 * @param id
	 *            task unique key
	 * @param newValue
	 *            value to set
	 * @param userId
	 * 			  owner id
	 */
	public static void updateProgress(Integer id, Integer newValue,
		Integer userId) {
		final String SQL = "UPDATE TT_TASK SET PROGRESS=? "
			+ "WHERE ID=? AND USER_ID=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, newValue);
			ps.setInt(2, id);
			ps.setInt(3, userId);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn, ps, null);
		}
	}
}
