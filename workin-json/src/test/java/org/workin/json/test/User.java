package org.workin.json.test;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private String loginName;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}