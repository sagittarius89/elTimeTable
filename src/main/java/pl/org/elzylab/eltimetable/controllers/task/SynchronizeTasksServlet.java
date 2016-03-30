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

import java.io.PrintWriter;
import java.util.List;

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
 * ==================================================================== Sends to
 * view data about tasks sorted by requested order (CREATION_TIME or PRIOR)
 *
 */
public class SynchronizeTasksServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9142430907695176954L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		UserData userData = Utils.getUserData(request);
		String orderBy = request.getParameter("orderBy");
		String tabId = request.getParameter("tabId");

		List<TaskDTO> newTasks = null;

		if ("DATE".equals(orderBy)) {
			newTasks = Tasks.getTasks(userData.getId(), "CREATION_TIME", tabId);
		} else {
			newTasks = Tasks.getTasks(userData.getId(), "PRIOR", tabId);
		}

		PrintWriter printWriter = Utils.getPrintWriter(response);
		printWriter.write(TasksUtils.serializeTasks(newTasks));
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
