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

package org.sniper.templet.view.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.templet.view.AbstractViewRender;

/**
 * Velocity视图渲染器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractVelocityViewRender extends AbstractViewRender {
	
	private VelocityEngine velocityEngine;
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	@Override
	public <K, V> boolean rende(String templetName, Map<K, V> context, Writer writer) throws IOException {
		VelocityContext velocityContext = MapUtils.isNotEmpty(context) ? new VelocityContext(context) : new VelocityContext();
		StringBuilder targetName = new StringBuilder(getPrefix())
				.append(StringUtils.trim(templetName)).append(getSuffix());
		
		if (velocityEngine == null) {
			synchronized(this) {
				if (velocityEngine == null)
					velocityEngine = buildEngine();
			}
		}
		
		return velocityEngine.mergeTemplate(targetName.toString(),
				getEncoding(), velocityContext, writer);
	}
	
	/**
	 * 构建Velocity模板引擎
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws IOException
	 */
	protected abstract VelocityEngine buildEngine() throws IOException;

}
