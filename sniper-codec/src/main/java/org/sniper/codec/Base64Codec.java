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
 * Create Date : 2016-5-6
 */

package org.sniper.codec;

import org.sniper.commons.util.Base64Utils;

/**
 * Base64编解码处理器
 * @author  Daniele
 * @version 1.0
 */
public class Base64Codec extends AbstractCodec {

	@Override
	public String encode(byte[] bytes) {
		return Base64Utils.encode(bytes);
	}

	@Override
	public String encode(String text, String charsetName) {
		return Base64Utils.encode(text, charsetName);
	}

	@Override
	public String decode(String encodedText, String charsetName) {
		return Base64Utils.decode(encodedText, charsetName);
	}

	@Override
	public byte[] decodeToBytes(String encodedText) {
		return Base64Utils.decodeToBytes(encodedText);
	}

}
