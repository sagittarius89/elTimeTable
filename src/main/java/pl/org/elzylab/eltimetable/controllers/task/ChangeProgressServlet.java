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

import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.beans.task.Tasks;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * changes status of given task in database and returns setted-up status
 * 
 *
 */
public class ChangeProgressServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6971991510180739556L;
	private static final String INC = "INC";
	private static final String DEC = "DEC";

	private static final int STEP = 10;

	@Override
	@SuppressWarnings("serial")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String idStr = request.getParameter("id");
		String currentValStr = request.getParameter("value");
		String changeTypeStr = request.getParameter("type");
		UserData userData = Utils.getUserData(request);

		Integer id = Integer.valueOf(idStr);
		Integer currentVal = Integer.valueOf(currentValStr);
		Integer changedVal = null;

		if (INC.equals(changeTypeStr)) {
			Tasks.updateProgress(id, changedVal = currentVal + STEP,
				userData.getId());
		} else if (DEC.equals(changeTypeStr)) {
			Tasks.updateProgress(id, changedVal = currentVal - STEP,
				userData.getId());
		}

		final Integer changedValFinal = changedVal;
		Utils.returnJson(response, new HashMap<String, Object>() {
			{
				put("progress", changedValFinal);
			}
		});
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
