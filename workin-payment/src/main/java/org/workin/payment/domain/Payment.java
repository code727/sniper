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

import java.math.BigDecimal;
import java.util.Date;

import org.workin.commons.entity.number.IdEntity;

/**
 * @description 支付记录实体
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Payment extends IdEntity {

	private static final long serialVersionUID = -6480268964029291858L;
	
	/** 订单编号 */
	private String orderId;
	
	 /** 第三方交易系统的订单号 */
    private String thirdOrderId;
    
    /** 支付金额 */
    private BigDecimal amount;

    /** 实际支付金额 */
    private BigDecimal payAmount;
    
    /** 支付时间 */
    private Date payTime;
    
    /** 支付状态 */
    private int status;
    
    /** 支付结果信息 */
    private String message;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getThirdOrderId() {
		return thirdOrderId;
	}

	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
