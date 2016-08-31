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

package org.workin.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.workin.commons.entity.number.Idable;

/**
 * 支付记录实体
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Payment extends Idable {

	/**
	 * 获取订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOrderId();

	/**
	 * 设置订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param orderId
	 */
	public void setOrderId(String orderId);

	/**
	 * 获取第三方订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getThirdOrderId();

	/**
	 * 设置第三方订单编号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdOrderId
	 */
	public void setThirdOrderId(String thirdOrderId);

	/**
	 * 获取支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public BigDecimal getAmount();

	/**
	 * 设置支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * 获取实际支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public BigDecimal getPayAmount();

	/**
	 * 设置实际支付金额
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param payAmount
	 */
	public void setPayAmount(BigDecimal payAmount);

	/**
	 * 获取支付时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Date getPayTime();

	/**
	 * 设置支付时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param payTime
	 */
	public void setPayTime(Date payTime);

	/**
	 * 获取支付状态
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getStatus();

	/**
	 * 设置支付状态
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param status
	 */
	public void setStatus(int status);

	/**
	 * 获取支付结果信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMessage();

	/**
	 * 设置支付结果信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 */
	public void setMessage(String message);
    
}
