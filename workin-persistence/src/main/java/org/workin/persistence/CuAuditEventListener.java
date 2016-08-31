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
 * Create Date : 2015-1-27
 */

package org.workin.persistence;

import org.workin.commons.entity.CuAuditable;
import org.workin.persistence.handler.AuditEventHandler;

/**
 * 新增/更新审核事件监听器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CuAuditEventListener implements PersistenceEventListener<CuAuditable> {
			
	private AuditEventHandler<CuAuditable> cuAuditEventHandler;
	
	public AuditEventHandler<CuAuditable> getCuAuditEventHandler() {
		return cuAuditEventHandler;
	}

	public void setCuAuditEventHandler(AuditEventHandler<CuAuditable> cuAuditEventHandler) {
		this.cuAuditEventHandler = cuAuditEventHandler;
	}

	@Override
	public void onPrePersist(CuAuditable entity) {
		cuAuditEventHandler.auditOnPrePersist(entity);
	}

	@Override
	public void onPreUpdate(CuAuditable entity) {
		cuAuditEventHandler.auditOnPreUpdate(entity);
	}

	@Override
	public void onPreRemove(CuAuditable entity) {
		cuAuditEventHandler.auditOnPreRemove(entity);
	}

	@Override
	public void onPreDestroy() {
		cuAuditEventHandler.auditOnDestroy();
	}
	
}
