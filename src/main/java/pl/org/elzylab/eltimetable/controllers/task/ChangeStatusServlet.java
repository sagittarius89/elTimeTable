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

import pl.org.elzylab.eltimetable.beans.task.TaskStatus;
import pl.org.elzylab.eltimetable.beans.task.Tasks;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * changes status of requested task (choosen by id)
 * 
 *
 */
public class ChangeStatusServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2144931168494001895L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String idStr = request.getParameter("id");
		String statusStr = request.getParameter("status");
		UserData userData = Utils.getUserData(request);
		
		Integer statusValue = Integer.valueOf(statusStr);
		Integer id = Integer.valueOf(idStr);

		TaskStatus status = TaskStatus.forValue(statusValue);

		Tasks.changeStatus(id, status, userData.getId());
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
