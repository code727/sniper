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
 * Create Date : 2015年12月21日
 */

package org.workin.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.workin.commons.entity.number.Idable;

/**
 * @description 订单接口对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Order extends Idable {
	
	/**
	 * @description 获取支付用户登录账号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getLoginName();

	/**
	 * @description 设置支付用户登录账号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param loginName
	 */
	public void setLoginName(String loginName);

	/**
	 * @description 获取订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOrderId();

	/**
	 * @description 设置订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param orderId
	 */
	public void setOrderId(String orderId);

	/**
	 * @description 获取支付类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getType();

	/**
	 * @description 设置支付类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 */
	public void setType(int type);

	/**
	 * @description 获取支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public BigDecimal getAmount();

	/**
	 * @description 设置支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * @description 获取购买数量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getQuantity();

	/**
	 * @description 设置购买数量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param quantity
	 */
	public void setQuantity(int quantity);

	/**
	 * @description 获取产品编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getProductId();

	/**
	 * @description 设置产品编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param productId
	 */
	public void setProductId(String productId);

	/**
	 * @description 获取产品名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getProductName();

	/**
	 * @description 设置产品名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param productName
	 */
	public void setProductName(String productName);

	/**
	 * @description 获取产品描述
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getDescription();

	/**
	 * @description 设置产品描述
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param description
	 */
	public void setDescription(String description);

	/**
	 * @description 获取下单提交时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Date getSubmitTime();

	/**
	 * @description 设置下单提交时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param submitTime
	 */
	public void setSubmitTime(Date submitTime);

	/**
	 * @description 设置产品单价
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public BigDecimal getPrice();

	/**
	 * @description 获取产品单价
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param price
	 */
	public void setPrice(BigDecimal price);

	/**
	 * @description 获取产品折扣
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public double getDiscount();

	/**
	 * @description 设置产品折扣
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param discount
	 */
	public void setDiscount(double discount);

	/**
	 * @description 获取线上/线下类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getO2oType();

	/**
	 * @description 设置线上/线下类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param o2oType
	 */
	public void setO2oType(int o2oType);

	/**
	 * @description 获取针对于线下用户输入的账号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAccount();

	/**
	 * @description 设置针对于线下用户输入的账号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param account
	 */
	public void setAccount(String account);

	/**
	 * @description 取针对于线下用户输入的密码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPassword();

	/**
	 * @description 设置对于线下用户输入的密码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param password
	 */
	public void setPassword(String password);

}
