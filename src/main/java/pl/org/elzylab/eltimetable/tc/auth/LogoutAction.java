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

package pl.org.elzylab.eltimetable.tc.auth;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * logout current user by invalidates session
 * 
 *
 */
public class LogoutAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9028367158977592656L;

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		response.sendRedirect("/eltimetable/loginform.jsp");
	}
}
