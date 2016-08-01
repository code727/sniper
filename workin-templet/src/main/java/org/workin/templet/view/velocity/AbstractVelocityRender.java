/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-1
 */

package org.workin.templet.view.velocity;

import java.io.Writer;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.templet.view.AbstractViewRender;

/**
 * @description Velocity视图渲染器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractVelocityRender extends AbstractViewRender {
	
	private VelocityEngine velocityEngine;
	
	protected AbstractVelocityRender() {
		this.velocityEngine = buildEngine();
	}
	
	@Override
	public <K, V> boolean rende(String templetName, Map<K, V> context, Writer writer) {
		VelocityContext velocityContext = (MapUtils.isNotEmpty(context) 
				? new VelocityContext(context) : new VelocityContext());
				
		StringBuilder targetName = new StringBuilder(getPrefix()).append(
				StringUtils.trim(templetName)).append(getSuffix());
		return velocityEngine.mergeTemplate(targetName.toString(), getEncoding(), velocityContext, writer);
	}
	
	/**
	 * @description 构建Velocity模板引擎
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract VelocityEngine buildEngine();

}
