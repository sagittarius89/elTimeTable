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
import pl.org.elzylab.eltimetable.beans.task.TaskStatus;
import pl.org.elzylab.eltimetable.beans.task.Tasks;
import pl.org.elzylab.eltimetable.beans.task.TasksUtils;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * creates task with data taken from from
 * 
 *
 */
public class CreateTaskServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8375884820306627591L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		UserData userData = Utils.getUserData(request);
		Integer userId = userData.getId();

		TaskDTO newTask = TasksUtils.createTaskFromRequestData(request);
		newTask.setStatus(TaskStatus.CREATED.getValue());

		Tasks.createTask(userId, newTask);
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
