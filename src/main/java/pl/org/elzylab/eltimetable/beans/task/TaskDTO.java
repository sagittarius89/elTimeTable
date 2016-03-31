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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * ==================================================================== Type
 * description
 * ====================================================================
 * 
 * Data transaction class which represents <b>TT_TASK</b> table from database.
 */
@SuppressWarnings("serial")
public class TaskDTO implements Serializable {
	private Integer id;
	private String name;
	private String description;
	private Timestamp time;
	private Long duration;
	private Integer status;
	private String color;
	private Integer progress;
	private Integer exp;
	private String notes;
	private Integer priority;
	private Integer tabId;
	private List<Integer> parents;
	private Timestamp creationTime;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getProgress() {
		return this.progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getExp() {
		return this.exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public List<Integer> getParents() {
		return this.parents;
	}

	/**
	 * tabId getter
	 *
	 * @return the tabId
	 */
	public Integer getTabId() {
		return tabId;
	}

	/**
	 * tabId setter
	 *
	 * @param tabId the tabId to set
	 */
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public void setParents(List<Integer> parents) {
		this.parents = parents;
	}

	/**
	 * creationTime getter
	 *
	 * @return the creationTime
	 */
	public Timestamp getCreationTime() {
		return creationTime;
	}

	/**
	 * creationTime setter
	 *
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
}
