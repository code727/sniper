/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-27
 */

package org.sniper.commons.base;

/**
 * 可响应的接口
 * @author  Daniele
 * @version 1.0
 */
public interface Responsible<C> extends Messenger {
	
	/**
	 * 获取响应码
	 * @author Daniele
	 * @return
	 */
	public C getCode();
	
}
