/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) 17 mar 2016 elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */
package pl.org.elzylab.eltimetable.beans.tabs;

import java.io.Serializable;

/**
 * ====================================================================
 * Description
 * ====================================================================
 * CLass represents tab as data transaction object
 * 
 *
 */
public class TabDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1036360639866866690L;
	
	private String id;
	private String name;
	private Integer order;
	private Boolean isDefault;
	
	/**
	 * id getter
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * id setter
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * name getter
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * name setter
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * order getter
	 *
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * order setter
	 *
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * isDefault getter
	 *
	 * @return the isDefault
	 */
	public Boolean isDefault() {
		return isDefault;
	}
	/**
	 * isDefault setter
	 *
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
}
