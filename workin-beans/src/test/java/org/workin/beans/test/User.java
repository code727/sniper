/*
 * Copyright (c) 2015 org.workin-commons 
 * Create Date : 2015-2-4
 */

package org.workin.beans.test;

import java.io.Serializable;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-4
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	
	private Long id;
	
	private String name;
	
	private int age;
	
	private Department department;
	
	private User user;
	
	private boolean vip;

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
	
	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return this.department;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
