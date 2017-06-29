/*
 * Copyright (c) 2015 org.sniper-commons 
 * Create Date : 2015-2-6
 */

package org.sniper.beans.test;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-6
 */
@SuppressWarnings("serial")
public class Company implements Serializable {

	private Long id;
	
	private String name;
	
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
