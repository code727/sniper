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

package org.workin.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.nosql.redis.dao.RedisCommandsDao;

/**
 * @description Redis会话共享库实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSessionRepository implements SessionRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisSessionRepository.class);
	
	/** Session前缀标识 */
	private String prefix;
	
	/** 存储会话数据的库索引 */
	private int dbIndex;
	
	/** 标识是否自动删除 */
	private boolean autoDelete = false;
	
	@Autowired
	private RedisCommandsDao redisCommandsDao;
	
	public RedisSessionRepository() {
		this.prefix = "shiro_session_";
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public RedisCommandsDao getRedisCommandsDao() {
		return redisCommandsDao;
	}

	public void setRedisCommandsDao(RedisCommandsDao redisCommandsDao) {
		this.redisCommandsDao = redisCommandsDao;
	}

	@Override
	public void saveSession(Session session) {
		if (session instanceof SimpleSession)
			((SimpleSession)session).setStopTimestamp(null);
		
		String sessionId = getPrefix() + session.getId();
		redisCommandsDao.set(dbIndex, sessionId, session);
		logger.debug("Set session id:" + sessionId);
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		if (autoDelete) {
			String cacheSessionId = getPrefix() + sessionId;
			redisCommandsDao.del(dbIndex, new Serializable[] { cacheSessionId });
			logger.debug("Delete session id:" + cacheSessionId);
		}
	}

	@Override
	public Session getSession(Serializable sessionId) {
		String cacheSessionId = getPrefix() + sessionId;
		Session session = redisCommandsDao.get(dbIndex, cacheSessionId);
		logger.debug("Get session id:" + cacheSessionId);
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		String keys = getPrefix() + "*";
		Collection<Session> sessions = redisCommandsDao.values(dbIndex, keys);
		logger.debug("Get all session keys:" + keys);
		return sessions;
	}

}
