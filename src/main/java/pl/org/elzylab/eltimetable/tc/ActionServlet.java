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
package pl.org.elzylab.eltimetable.tc;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ====================================================================
 * Description
 * ====================================================================
 * HttpServlet wrapper with mirror functionality for {@link HttpServlet#doGet}
 * and {@link HttpServlet#doPost}
 * 
 *
 */
public abstract class ActionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7437212822302475785L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doRequest(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void
		doPost(HttpServletRequest request, HttpServletResponse response) {

		doRequest(request, response);
	}

	/**
	 * Wrapper around action executor. If there is some result of action prints
	 * it to response with setup headers
	 * 
	 * @param request
	 * @param response
	 */
	private void doRequest(HttpServletRequest request,
		HttpServletResponse response) {
		String result = execute(request, response);

		if (result != null) {
			PrintWriter pw = Utils.getPrintWriter(response);
			pw.print(result);
			pw.flush();
		}
	}

	/**
	 * Action method with proper functionality which is realized by this servlet
	 * 
	 * @param request
	 * @param response
	 */
	protected abstract String execute(HttpServletRequest request,
		HttpServletResponse response);
}
