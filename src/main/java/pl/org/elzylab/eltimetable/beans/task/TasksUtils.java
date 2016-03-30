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

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import pl.org.elzylab.eltimetable.tc.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * ====================================================================
 * Description
 * ==================================================================== Storage
 * for static methods connected with Task.
 */
public class TasksUtils {

	public static final String TASKS_KEY = "tasks";

	private static Type mListType = new TypeToken<List<TaskDTO>>() {
	}.getType();

	/**
	 * Takes pure json from request and deserialize it to list of DTO objects
	 * 
	 * @param request
	 * @return list of DTO objects
	 */
	@SuppressWarnings("unchecked")
	public static List<TaskDTO> retrieveTasksFromRequest(
		HttpServletRequest request) {

		try {
			String tasksJson = request.getParameter(TASKS_KEY);
			Gson gson = new Gson();

			return (List<TaskDTO>) gson.fromJson(tasksJson, mListType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Resolves two list of task new vs old. For now there is no resolving. Just
	 * rewriting new list on place of old @TODO
	 * 
	 * @param newTasks
	 * @param oldTasks
	 * @return resolved tasks list
	 */

	public static List<TaskDTO> resolveTaskLists(List<TaskDTO> newTasks,
		List<TaskDTO> oldTasks) {

		newTasks = oldTasks;

		return null;
	}

	/**
	 * Serialize list of task data transaction objects to JSON
	 * 
	 * @param resolvedTasks
	 *            list of data transaction object
	 * @return stringified json with data
	 */
	public static String serializeTasks(List<TaskDTO> resolvedTasks) {
		Gson gson = new Gson();

		return gson.toJson(resolvedTasks);
	}

	/**
	 * Creates data transaction object from data stored in request (from view
	 * side).
	 * 
	 * @param request
	 * @return task data transaction object
	 */
	public static TaskDTO createTaskFromRequestData(HttpServletRequest request) {
		TaskDTO newTask = new TaskDTO();

		newTask.setName(request.getParameter("taskName"));
		newTask.setDescription(request.getParameter("description"));

		String timestamp = request.getParameter("date");
		newTask.setTime(Utils.stringToTimestamp(timestamp));

		String duration = request.getParameter("duration");
		String unit = request.getParameter("unit");

		Long durationValue = Long.parseLong(duration);

		if ("minutes".equals(unit)) {
			durationValue *= 60000;
		} else if ("hours".equals(unit)) {
			durationValue *= 60000 * 60;
		}

		newTask.setDuration(durationValue);
		newTask.setColor(request.getParameter("color"));
		newTask.setExp(getNumberFromRequest(request, "exp"));
		newTask.setPriority(getNumberFromRequest(request, "prior"));
		newTask.setNotes(request.getParameter("notes"));
		newTask.setStatus(getNumberFromRequest(request, "status"));
		newTask.setProgress(getNumberFromRequest(request, "progress"));
		newTask.setTabId(getNumberFromRequest(request, "tabId"));

		return newTask;
	}

	/**
	 * Takes param from request and parse it's value to Integer
	 * 
	 * @param request
	 * @param paramName
	 *            name of field in request
	 * @return parsed value or null if not numeric value or null
	 */
	private static Integer getNumberFromRequest(HttpServletRequest request,
		String paramName) {
		Integer value = null;
		try {
			value = Integer.parseInt(request.getParameter(paramName));
		} catch (NumberFormatException | NullPointerException e) {
		}

		return value;
	}
}
