/*
 * Copyright (c) 2015 org.workin-commons 
 * Create Date : 2015-2-6
 */

package org.workin.beans.test;

import java.io.Serializable;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-6
 */
@SuppressWarnings("serial")
public class Company implements Serializable {

	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
