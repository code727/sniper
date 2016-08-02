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

package org.workin.templet.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @description 视图渲染器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ViewRender {
		
	/**
	 * @description 将数据渲染到指定视图模板后再由写入器输出
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param templetName
	 * @param context
	 * @param writer
	 * @return
	 * @throws IOException
	 */
	public <K, V> boolean rende(String templetName, Map<K,V> context, Writer writer) throws IOException;

}
