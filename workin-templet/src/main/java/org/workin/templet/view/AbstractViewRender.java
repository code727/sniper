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
 * Create Date : 2016年8月1日
 */

package org.workin.templet.view;

import org.workin.support.codec.CodecSupport;

/**
 * @description 视图渲染器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractViewRender extends CodecSupport implements ViewRender {
	
	/** 目标视图模板前缀 */
	private String prefix = "";
	
	/** 目标视图模板后缀 */
	private String suffix = "";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if (prefix != null)
			this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		if (suffix != null)
			this.suffix = suffix;
	}

}
