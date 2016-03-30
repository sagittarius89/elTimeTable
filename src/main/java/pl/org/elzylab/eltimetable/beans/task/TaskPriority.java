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

package pl.org.elzylab.eltimetable.beans.task;

/**
 * ====================================================================
 * Description
 * ==================================================================== Enum
 * mapping task priority value (numeric) from database on human understandable
 * language
 */
public enum TaskPriority {

	SUPER_IMPORTANT(0, "super important"),
	VERY_IMPORTANT(1, "very important"),
	IMPORTANT(2, "important"),
	NORMAL(3, "normal"),
	NOT_IMPORTANT(4, "not important"),
	FOR_LATER(5, "for later"),
	FOR_VERY_LATER(6, "for very later");

	private String description;
	private Integer value;

	TaskPriority(Integer value, String description) {
		this.description = description;
		this.value = value;
	}

	/**
	 * Human understandable description of priority
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Numeric priority value from database
	 * 
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}
}
