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
 * Create Date : 2015-5-11
 */

package org.sniper.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.command.RedisCommands;

/**
 * Redis会话共享库实现类
 * @author  Daniele
 * @version 1.0
 */
public class RedisSessionRepository implements SessionRepository, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisSessionRepository.class);
	
	/** Session前缀标识 */
	private String prefix;
	
	/** 存储会话数据的库名称 */
	private String dbName;
		
	private RedisCommands redisCommands;
	
	public RedisSessionRepository() {
		this.prefix = "shiro_session_";
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public RedisCommands getRedisCommands() {
		return redisCommands;
	}

	public void setRedisCommands(RedisCommands redisCommands) {
		this.redisCommands = redisCommands;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.redisCommands == null)
			throw new IllegalArgumentException("Property 'redisCommands' is required");
	}

	@Override
	public void saveSession(Session session) {
		if (session instanceof SimpleSession)
			((SimpleSession) session).setStopTimestamp(null);
		
		String sessionId = getPrefix() + session.getId();
		redisCommands.setIn(dbName, sessionId, session);
		logger.debug("Set session id:{}", sessionId);
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		String cacheSessionId = getPrefix() + sessionId;
		redisCommands.del(dbName, new Serializable[] { cacheSessionId });
		logger.debug("Delete session id:{}", cacheSessionId);
	}

	@Override
	public Session getSession(Serializable sessionId) {
		String cacheSessionId = getPrefix() + sessionId;
		Session session = redisCommands.getIn(dbName, cacheSessionId);
		logger.debug("Get session id:{}", cacheSessionId);
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		String pattern = getPrefix() + StringUtils.ANY;
		Collection<Session> sessions = redisCommands.valuesByPattern(dbName, pattern);
		logger.debug("Get all session keys:{}", pattern);
		return sessions;
	}

}
