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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.beans.tabs.TabDTO;
import pl.org.elzylab.eltimetable.beans.tabs.Tabs;
import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * apply changes in tabs done by user on view (update, delete, create)
 *
 */
public class UpdateTabsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792510928185858949L;

	@Override
	@SuppressWarnings("serial")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String newTabsStr = request.getParameter("newTabs");
		String deletedTabsStr = request.getParameter("deletedTabs");
		String updatedTabsStr = request.getParameter("updatedTabs");
		UserData ud = Utils.getUserData(request);
		
		Type listOfTabsType = new TypeToken<ArrayList<TabDTO>>() {
		}.getType();
		Gson gson = new Gson();

		List<TabDTO> newTabs = gson.fromJson(newTabsStr, listOfTabsType);
		List<TabDTO> tabsToUpdate =
			gson.fromJson(updatedTabsStr, listOfTabsType);
		List<Integer> tabsToDelete =
			Utils.getListOfIntegerFromJson(deletedTabsStr);
		
		Tabs.createNewTabs(newTabs, ud.getId());
		Tabs.updateTabs(tabsToUpdate, ud.getId());
		Tabs.deleteTabs(tabsToDelete, ud.getId());
		
		Utils.returnJson(response, new HashMap<String,Object>() {{
			put("status", "OK");
		}});
	}

	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
}
