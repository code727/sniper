/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-11-13
 */

package org.workin.payment.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 订单实体对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -4569426721006936232L;
	
	/** ID标识 */
	private Long id;
	
	/** 下单用户登录名 */
	private String loginName;
	
	/** 订单编号 */
	private String orderId;
	
	/** o2o类型 */
	private int o2oType;
	
	/** 选择的第三方支付类型 */
	private int type;
	
	/** 交易金额 */
	private BigDecimal amount;
	
	/** 商品购买数 */
	private int quantity = 1;
	
	/** 商品ID */
	private String mercId;
	
	/** 商品名称 */
	private String mercName;
	
	/** 描述 */
	private String description;
	
	/** 商品单价 */
	private BigDecimal price;
	
	/** 折扣 */
	private double discount = 1;
	
	/** 下单时间 */
	private Date submitTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMercId() {
		return mercId;
	}

	public void setMercId(String mercId) {
		this.mercId = mercId;
	}

	public String getMercName() {
		return mercName;
	}

	public void setMercName(String mercName) {
		this.mercName = mercName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getO2oType() {
		return o2oType;
	}

	public void setO2oType(int o2oType) {
		this.o2oType = o2oType;
	}
	
}