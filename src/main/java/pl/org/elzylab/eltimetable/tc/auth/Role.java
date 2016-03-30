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

/**
 * ====================================================================
 * Description
 * ====================================================================
 * Class represents role in system
 * 
 * Now roles are not used yet
 * Implementation is @TODO
 * 
 *
 */
public class Role implements Principal 
{
	private String name;
	
	public Role(String name) 
	{
		super();
		this.name = name;
	}
	
	public void getName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getName() {
		
		return this.name;
	}
}
