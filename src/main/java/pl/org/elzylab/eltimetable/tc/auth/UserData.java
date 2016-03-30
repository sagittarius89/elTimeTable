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

import java.security.Principal;

import pl.org.elzylab.eltimetable.beans.user.UserDTO;

/**
 * ====================================================================
 * Description
 * ====================================================================
 * Class keeps user data in session.
 *
 */
public class UserData implements Principal
{
	private UserDTO user;
	
	public UserData(UserDTO user) 
	{
		super();
		this.user = user;
	}

	@Override
	public String getName()
	{
		return this.user.getName();
	}
	
	public Integer getId()
	{
		return this.user.getId();
	}
}
