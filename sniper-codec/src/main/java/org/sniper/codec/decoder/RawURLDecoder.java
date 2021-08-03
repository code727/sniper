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
 * Create Date : 2021-8-2
 */

package org.sniper.codec.decoder;

import java.io.UnsupportedEncodingException;

import org.sniper.codec.CodecSupport;
import org.sniper.commons.util.StringUtils;

/**
 * RFC-2396标准的URL解码器实现类
 * @author Daniele
 * @version 1.0
 */
public class RawURLDecoder extends CodecSupport implements StringDecoder {

	@Override
	public String decode(String message) throws UnsupportedEncodingException {
		return this.decode(message, null);
	}

	@Override
	public String decode(String message, String encoding) throws UnsupportedEncodingException {

		boolean needToChange = false;
		int numChars = message.length();
		StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
		int i = 0;

		if (StringUtils.isBlank(encoding))
			encoding = getEncoding();

		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = message.charAt(i);
			switch (c) {
//			case '+':
//				sb.append(' ');
//				i++;
//				needToChange = true;
//				break;
			case '%':
				try {
					if (bytes == null)
						bytes = new byte[(numChars - i) / 3];
					int pos = 0;

					while (((i + 2) < numChars) && (c == '%')) {
						int v = Integer.parseInt(message.substring(i + 1, i + 3), 16);
						if (v < 0) {
							throw new IllegalArgumentException("RawURLDecoder: Illegal hex characters in escape (%) pattern - negative value");
						}

						bytes[pos++] = (byte) v;
						i += 3;
						if (i < numChars)
							c = message.charAt(i);
					}
					if ((i < numChars) && (c == '%'))
						throw new IllegalArgumentException("RawURLDecoder: Incomplete trailing escape (%) pattern");

					sb.append(new String(bytes, 0, pos, encoding));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("RawURLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
				}
				needToChange = true;
				break;
			default:
				sb.append(c);
				i++;
				break;
			}
		}

		return (needToChange ? sb.toString() : message);
	}

}
