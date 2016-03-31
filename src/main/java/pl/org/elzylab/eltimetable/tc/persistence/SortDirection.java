/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) 31 mar 2016 elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */
package pl.org.elzylab.eltimetable.tc.persistence;

/**
 * ====================================================================
 * Description
 * ====================================================================
 * Enum describes sort direction in SQL (comparing sent parameter from view
 * to elements of this enum, we are protected from SQL injection - 
 * passing sort direction parameter strict from view someone could inject 
 * destructive SQL code).
 * 
 *
 */
public enum SortDirection {
	ASC, DESC
}
