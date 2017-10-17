/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-10-13
 */

package org.sniper.http.httpclient.v4.handler.multipart;

/**
 * 资源类型的Multipart请求体处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSourceMultipartBodyHandler extends AbstractMultipartBodyHandler {
	
	protected static final String SINGLE_FILE_PARAMETER_NAME = "file";
	protected static final String MULTI_FILE_PARAMETER_NAME = "files";
		
	protected AbstractSourceMultipartBodyHandler(Class<?>... supportedBodyTypes) {
		super(supportedBodyTypes);	
	}
		
}
