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
 * Create Date : 2015-8-24
 */

package org.workin.jms.util;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.workin.jms.core.strategy.SharedStrategy;

/**
 * JMS工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JmsUtils {
	
	/**
	 * 判断是否为客户端回复会话模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @return
	 * @throws JMSException
	 */
	public static boolean isClientAcknowledge(Session session) throws JMSException {
		return (session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE);
	}
	
	/**
	 * 回滚当前会话的事务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @throws JMSException
	 */
	public static void rollback(Session session) throws JMSException {
		if (session != null)
			session.rollback();
	}
	
	/**
	 * 自动回滚当前会话的事务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @throws JMSException
	 */
	public static void autoRollback(Session session, SharedStrategy strategy, ConnectionFactory connectionFactory) throws JMSException {
		if (strategy.isAutoRollback() && hasTransactional(session, strategy, connectionFactory))
			rollback(session);
	}
	
	/**
	 * 判断当前会话是否具备本地事务特性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param connectionFactory
	 * @return
	 */
	public static boolean isSessionLocallyTransacted(Session session, SharedStrategy strategy, ConnectionFactory connectionFactory) {
		return strategy.isSessionTransacted() && !ConnectionFactoryUtils.isSessionTransactional(session, connectionFactory);
	}

	/**
	 * 判断会话是否具有事务性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param connectionFactory
	 * @return
	 */
	public static boolean hasTransactional(Session session, SharedStrategy strategy, ConnectionFactory connectionFactory) {
		try {
			return session.getTransacted() && isSessionLocallyTransacted(session, strategy, connectionFactory);
		} catch (JMSException e) {
			return false;
		}
	}

}
