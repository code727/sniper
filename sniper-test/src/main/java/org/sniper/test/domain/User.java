/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2017年12月29日
 */

package org.sniper.test.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sniper.test.annotation.Testable;

/**
 * 用户测试对象
 * @author  Daniele
 * @version 1.0
 */
public class User extends Person implements Cloneable {
	
	private static final long serialVersionUID = 2470588830439887184L;

	public static final String DEFAULT_PASSWORD = "123456";
	
	private Long id;
	
	@Testable
	private String loginName;
	
	private String password = DEFAULT_PASSWORD;
	
	private byte gender;
	
	private int age;
	
	private boolean married;
	
	private double vision;
	
	private BigDecimal amount;
	
	private Department department;
	
	private User boss;
	
	private String[] keywords;
	
	private List<String> addresses;
	
	private Map<String, Object> detail;
	
	private Date createTime;
	
	private Date updateTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Testable
	public String getLoginName() {
		return loginName;
	}

	@Testable
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Testable
	public boolean isMarried() {
		return married;
	}
	
	@Testable
	public void setMarried(boolean married) {
		this.married = married;
	}

	public double getVision() {
		return vision;
	}

	public void setVision(double vision) {
		this.vision = vision;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getBoss() {
		return boss;
	}

	public void setBoss(User boss) {
		this.boss = boss;
	}
	
	public Map<String, Object> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, Object> detail) {
		this.detail = detail;
	}
	
	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	
	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	@Testable
	public User clone() {  
        try {
			return (User) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
    }  

}
