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
 * Create Date : 2015-12-10
 */

package org.workin.commons.util;

import java.io.UnsupportedEncodingException;

/**
 * @description Base64编解码工具
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Base64Utils {
	
	static private final int BASELENGTH = 128;
	static private final int LOOKUPLENGTH = 64;
	static private final int TWENTYFOURBITGROUP = 24;
	static private final int EIGHTBIT = 8;
	static private final int SIXTEENBIT = 16;
	static private final int FOURBYTE = 4;
	static private final int SIGN = -128;
	static private final char PAD = '=';
	static final private byte[] base64Alphabet = new byte[BASELENGTH];
	static final private char[] lookUpBase64Alphabet = new char[LOOKUPLENGTH];

    static {
    	
        for (int i = 0; i < BASELENGTH; ++i) 
            base64Alphabet[i] = -1;
        
        for (int i = 'Z'; i >= 'A'; i--) 
            base64Alphabet[i] = (byte) (i - 'A');
        
        for (int i = 'z'; i >= 'a'; i--)
            base64Alphabet[i] = (byte) (i - 'a' + 26);

        for (int i = '9'; i >= '0'; i--)
            base64Alphabet[i] = (byte) (i - '0' + 52);

        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = 0; i <= 25; i++) 
            lookUpBase64Alphabet[i] = (char) ('A' + i);

        for (int i = 26, j = 0; i <= 51; i++, j++) 
            lookUpBase64Alphabet[i] = (char) ('a' + j);

        for (int i = 52, j = 0; i <= 61; i++, j++) 
            lookUpBase64Alphabet[i] = (char) ('0' + j);
        
        lookUpBase64Alphabet[62] = (char) '+';
        lookUpBase64Alphabet[63] = (char) '/';
    }

    private static boolean isWhiteSpace(char octect) {
        return (octect == 0x20 || octect == 0xd || octect == 0xa || octect == 0x9);
    }
        
    private static boolean isPad(char octect) {
        return (octect == PAD);
    }

    private static boolean isData(char octect) {
        return (octect < BASELENGTH && base64Alphabet[octect] != -1);
    }
    
    /**
     * @description 将明文按默认字符集进行编码处理
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param plaintext
     * @return
     */
    public static String encode(String plaintext) {
    	return encode(plaintext, null);
    }
    
    /**
     * @description 将明文按指定字符集格式进行编码处理
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param plaintext
     * @param charsetName
     * @return
     */
    public static String encode(String plaintext, String charsetName) {
    	AssertUtils.assertNotNull(plaintext, "Base64 encode plaintext must not be null.");
    	try {
			return encode(plaintext.getBytes(StringUtils.isNotBlank(charsetName) ? 
					charsetName : CodecUtils.UTF8_ENCODING));
		} catch (UnsupportedEncodingException e) {
			return encode(plaintext, CodecUtils.UTF8_ENCODING);
		}
    }
       
    /**
     * @description 对字节数组进行编码处理
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
    	AssertUtils.assertTrue(ArrayUtils.isNotEmpty(bytes), "Encode byte array must not be null or empty.");
    	
    	int length = bytes.length * EIGHTBIT;
        int fewerThan24bits = length % TWENTYFOURBITGROUP;
        int numberTriplets = length / TWENTYFOURBITGROUP;
        int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;
        char encodedData[] = null;

        encodedData = new char[numberQuartet * 4];

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
        int encodedIndex = 0;
        int dataIndex = 0;

        for (int i = 0; i < numberTriplets; i++) {
            b1 = bytes[dataIndex++];
            b2 = bytes[dataIndex++];
            b3 = bytes[dataIndex++];

            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
        }

        // form integral number of 6-bit groups
        if (fewerThan24bits == EIGHTBIT) {
            b1 = bytes[dataIndex];
            k = (byte) (b1 & 0x03);
            
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex++] = PAD;
            encodedData[encodedIndex++] = PAD;
        } else if (fewerThan24bits == SIXTEENBIT) {
            b1 = bytes[dataIndex];
            b2 = bytes[dataIndex + 1];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex++] = PAD;
        }

        return new String(encodedData);
    }
    
    /**
     * @description 将被编码的文本内容解码成默认字符集的字符串
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param encoded
     * @return
     */
    public static String decode(String encoded) {
    	return decode(encoded, null);
    }
    
    /**
     * @description 将被编码的文本内容解码成指定字符集的字符串
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param encoded
     * @param charsetName
     * @return
     */
    public static String decode(String encoded, String charsetName) {
    	try {
			return new String(decodeToBytes(encoded), StringUtils.isNotBlank(charsetName) ? 
					charsetName : CodecUtils.UTF8_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return decode(encoded, CodecUtils.UTF8_ENCODING);
		}
    }
        
    /**
     * @description 将被编码的文本内容解码成字节数组
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param encoded
     * @return
     */
    public static byte[] decodeToBytes(String encoded) {
    	AssertUtils.assertTrue(StringUtils.isNotEmpty(encoded), "Decode string must not be null or empty.");
    	
        char[] base64Data = encoded.toCharArray();
        int len = removeWhiteSpace(base64Data);
        if (len % FOURBYTE != 0) 
            return null;

        int numberQuadruple = (len / FOURBYTE);
        if (numberQuadruple == 0)
            return new byte[0];

        byte decodedData[] = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
        char d1 = 0, d2 = 0, d3 = 0, d4 = 0;

        int i = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        decodedData = new byte[(numberQuadruple) * 3];

        for (; i < numberQuadruple - 1; i++) {

            if (!isData((d1 = base64Data[dataIndex++])) || !isData((d2 = base64Data[dataIndex++]))
                || !isData((d3 = base64Data[dataIndex++]))
                || !isData((d4 = base64Data[dataIndex++]))) {
                return null;
            }

            b1 = base64Alphabet[d1];
            b2 = base64Alphabet[d2];
            b3 = base64Alphabet[d3];
            b4 = base64Alphabet[d4];

            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
        }

        if (!isData((d1 = base64Data[dataIndex++])) || !isData((d2 = base64Data[dataIndex++]))) 
            return null;

        b1 = base64Alphabet[d1];
        b2 = base64Alphabet[d2];

        d3 = base64Data[dataIndex++];
        d4 = base64Data[dataIndex++];
        if (!isData((d3)) || !isData((d4))) {
        	//Check if they are PAD characters
            if (isPad(d3) && isPad(d4)) {
                if ((b2 & 0xf) != 0) {
                	//last 4 bits should be zero
                    return null;
                }
                byte[] tmp = new byte[i * 3 + 1];
                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                return tmp;
            } else if (!isPad(d3) && isPad(d4)) {
                b3 = base64Alphabet[d3];
                if ((b3 & 0x3) != 0) { 
                	//last 2 bits should be zero
                    return null;
                }
                byte[] tmp = new byte[i * 3 + 2];
                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
                tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                return tmp;
            } else {
                return null;
            }
        } else { 
            b3 = base64Alphabet[d3];
            b4 = base64Alphabet[d4];
            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
        }

        return decodedData;
    }

    private static int removeWhiteSpace(char[] data) {
        if (data == null) {
            return 0;
        }

        int newSize = 0;
        int len = data.length;
        for (int i = 0; i < len; i++) {
            if (!isWhiteSpace(data[i])) {
                data[newSize++] = data[i];
            }
        }
        return newSize;
    }
        
}
