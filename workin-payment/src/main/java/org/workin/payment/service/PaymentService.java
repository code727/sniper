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
 * Create Date : 2015-11-17
 */

package org.workin.payment.service;

import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.payment.domain.Payment;

/**
 * @description 支付基础服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PaymentService {
	
	/**
	 * @description 保存支付记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param payment
	 * @return
	 * @throws Exception
	 */
	public CodeMessageModel save(Payment payment) throws Exception;
	
	/**
	 * @description 根据订单编号查询支付记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param orderId
	 * @return
	 */
	public Payment findByOrderId(String orderId);
	
	/**
	 * @description 更新支付记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param payment
	 * @return
	 * @throws Exception
	 */
	public CodeMessageModel update(Payment payment) throws Exception;

}
