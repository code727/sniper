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
 * Create Date : 2016年1月5日
 */

package org.workin.http.httpclient.v4.handler.request;

import org.workin.commons.util.StringUtils;
import org.workin.http.HttpForm;
import org.workin.support.codec.CodecSupport;

/**
 * @description 请求处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractRequestHandler extends CodecSupport implements RequestHandler {

	@Override
	public String getEncoding(HttpForm form) {
		String encoding = form.getEncoding();
		// 获取表单绑定的字符集编码，否则返回当前处理器绑定的字符集编码
		return StringUtils.isNotBlank(encoding) ? encoding : getEncoding();
	}

}
