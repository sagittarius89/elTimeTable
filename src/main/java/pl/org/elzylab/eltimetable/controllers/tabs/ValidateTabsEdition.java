/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) 20 mar 2016 elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */
package pl.org.elzylab.eltimetable.controllers.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.beans.tabs.Tabs;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;


/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * validates if given tasks can be removed (check if there exist any active task
 * for them).
 *
 */
public class ValidateTabsEdition extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792510928185858949L;

	@Override
	@SuppressWarnings("serial")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String deletedTabsStr = request.getParameter("ids");
		UserData ud = Utils.getUserData(request);

		List<Integer> tabsIds = Utils.getListOfIntegerFromJson(deletedTabsStr);
		List<Integer> result = new ArrayList<Integer>();

		for (Integer i : tabsIds) {
			if (!Tabs.checkIfTabCanBeRemoved(i, ud.getId())) {
				result.add(i);
			}
		}

		Utils.returnJson(response, new HashMap<String, Object>() {
			{
				if (result.isEmpty()) {
					put("status", "OK");
				} else {
					put("status", "ERROR");
					put("result", result);
				}
			}
		});
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
