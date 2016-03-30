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

package pl.org.elzylab.eltimetable.controllers.task;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.beans.task.TaskDTO;
import pl.org.elzylab.eltimetable.beans.task.Tasks;
import pl.org.elzylab.eltimetable.beans.task.TasksUtils;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * overrides task data (with given id) with data taken from form.
 * 
 *
 */
public class EditTaskServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		TaskDTO task = TasksUtils.createTaskFromRequestData(request);
		UserData userData = Utils.getUserData(request);
		
		Integer taskId = Integer.valueOf(request.getParameter("id"));
		task.setId(taskId);

		Tasks.updateTask(userData.getId(), task);
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
