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
package pl.org.elzylab.eltimetable.tc.filters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.org.elzylab.eltimetable.tc.Utils;
import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ====================================================================
 *
 * 
 *
 */
public class ValidationFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	@SuppressWarnings("serial")
	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws IOException, ServletException {
		
		try
		{
			UserData ud = Utils.getUserData((HttpServletRequest) request);
			
			if(ud == null)
			{
				((HttpServletResponse)response).sendRedirect("/login.jsp");
				return;
			}
			
			chain.doFilter(request, response);
		}
		catch(Throwable t)
		{
			Utils.returnJson((HttpServletResponse)response, new HashMap<String,Object>() {{
				put("status", "error");
				put("data", t.getMessage());
			}});
			
			t.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
