/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) 2 kwi 2016 elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */
package pl.org.elzylab.eltimetable.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.tc.ActionServlet;

/**
 * ====================================================================
 * Description
 * ==================================================================== Ping
 * servlet returns information if user is still logged in
 * 
 *
 */
public class PingServlet extends ActionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2933176562783748369L;

	private final String JSON_OK = "{ \"PING\": \"OK\" }";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.org.elzylab.eltimetable.tc.ActionServlet#execute(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String execute(HttpServletRequest request,
		HttpServletResponse response) {

		return JSON_OK;
	}

}
