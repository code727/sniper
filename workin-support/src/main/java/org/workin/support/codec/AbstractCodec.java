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
 * Create Date : 2016年1月8日
 */

package org.workin.support.codec;

import java.io.UnsupportedEncodingException;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 编解码器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractCodec implements Codec {

	@Override
	public String encode(String plaintext) {
		return encode(plaintext, null);
	}

	@Override
	public String encode(String plaintext, String charsetName) {
		AssertUtils.assertNotNull(plaintext, "Encode plaintext must not be null.");
    	try {
			return encode(plaintext.getBytes(StringUtils.isNotBlank(charsetName) ? 
					charsetName : CodecUtils.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			return encode(plaintext, CodecUtils.DEFAULT_ENCODING);
		}
	}

	@Override
	public String decode(String encoded) {
		return decode(encoded, null);
	}

	@Override
	public String decode(String encoded, String charsetName) {
		try {
			return new String(decodeToBytes(encoded), StringUtils.isNotBlank(charsetName) ? 
					charsetName : CodecUtils.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return decode(encoded, CodecUtils.DEFAULT_ENCODING);
		}
	}

}
