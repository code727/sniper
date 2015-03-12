/*
 * Copyright (c) 2015 org.workin-commons 
 * Create Date : 2015-2-4
 */

package org.worin.support.test.bean;

import java.io.Serializable;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-4
 */
@SuppressWarnings("serial")
public class Department implements Serializable {
	
	private Long id;
	
	private Company company;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
