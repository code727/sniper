/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-7-15
 */

package org.sniper.codec.encoder;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessController;
import java.util.BitSet;

import org.sniper.codec.CodecSupport;
import org.sniper.commons.util.StringUtils;

import sun.security.action.GetPropertyAction;

/**
 * RFC-2396标准的URL编码器实现类
 * @author  Daniele
 * @version 1.0
 */
public class RawURLEncoder extends CodecSupport implements StringEncoder {
	
	/** 不需要参与编码的字符集 */
	static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');
    static String dfltEncName = null;

    static {
        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
//        dontNeedEncoding.set(' '); 
        
        /* 排除如下特殊字符 */
        dontNeedEncoding.set('$');
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('+');
        dontNeedEncoding.set('!');
        dontNeedEncoding.set('*');
        dontNeedEncoding.set('’');
        dontNeedEncoding.set('(');
        dontNeedEncoding.set(')');
        
        /* 排除如下URL保留字符 */
        dontNeedEncoding.set('&');
        dontNeedEncoding.set('/');
        dontNeedEncoding.set(':');
        dontNeedEncoding.set(';');
        dontNeedEncoding.set('=');
        dontNeedEncoding.set('?');
        dontNeedEncoding.set('@');

        dfltEncName = AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
    }

	@Override
	public String encode(String message) throws UnsupportedEncodingException {
		return this.encode(message, null);
	}

	@Override
	public String encode(String message, String encoding) throws UnsupportedEncodingException {
		boolean needToChange = false;
        StringBuffer out = new StringBuffer(message.length());
        Charset charset;
        CharArrayWriter charArrayWriter = new CharArrayWriter();

        if (StringUtils.isBlank(encoding))
        	encoding = getEncoding();

        try {
            charset = Charset.forName(encoding);
        } catch (IllegalCharsetNameException e) {
            throw new UnsupportedEncodingException(encoding);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(encoding);
        }

        for (int i = 0; i < message.length();) {
            int c = (int) message.charAt(i);
            if (dontNeedEncoding.get(c)) {
//                if (c == ' ') {
//                    c = '+';
//                    needToChange = true;
//                }
                out.append((char)c);
                i++;
            } else {
                do {
                    charArrayWriter.write(c);
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        if ( (i+1) < message.length()) {
                            int d = (int) message.charAt(i+1);
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < message.length() && !dontNeedEncoding.get((c = (int) message.charAt(i))));

                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (int j = 0; j < ba.length; j++) {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }

        return (needToChange? out.toString() : message);
    }
		
}
