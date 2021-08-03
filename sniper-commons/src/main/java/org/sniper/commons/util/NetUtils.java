/*
 * Copyright 2014 the original author or authors.
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
 * Create Date : 2014-12-22
 */

package org.sniper.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 网络通信工具类
 * @author  Daniele
 * @version 1.0
 */
public class NetUtils {
	
	private NetUtils() {}
	
	/** HTTP协议 */
	public static final String HTTP_PROTOCOL = "http";
	
	/** HTTPS协议 */
	public static final String HTTPS_PROTOCOL = "https";
	
	/** 默认HTTP协议端口号(80) */
	public static final int DEFAULT_HTTP_PORT = 80;
	
	/** 默认HTTPS协议端口号(443) */
	public static final int DEFAULT_HTTPS_PORT = 443;
	
	/** 最小端口号 */
	public static final int MIN_PORT = 0;
	
	/** 最大端口号 */
	public static final int MAX_PORT = 65535;
	
	/** 默认超时毫秒数 */
	public static final int DEFAULT_TIMEOUT_MSEC = 3000;
	
	/** 默认超时秒数 */
	public static final int DEFAULT_TIMEOUT_SEC = 3;
	
	/** 回送地址 */
	public static final String LOOPBACK_ADDRESS = "127.0.0.1";
	 
	/**
	 * 获取本机的IP地址
	 * @author Daniele 
	 * @return
	 * @throws UnknownHostException 
	 * @throws Exception
	 */
	public static String getHostIp() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostAddress();
	}
	
	/**
	 * 获取本机的名称
	 * @author Daniele 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostName();
	}
	
	/**
	 * 发送HTTP httpGet请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url) throws IOException {
		return httpGet(url, "", "");
	}
	
	/**
	 * 发送带参数的HTTP httpGet请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, String parameters) throws IOException {
		return httpGet(url, parameters, "");
	}
	
	/**
	 * 发送带参数的HTTP httpGet请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static <V> String httpGet(String url, Map<String, V> parameters) throws IOException {
		return httpGet(url, MapUtils.joinQueryString(parameters), "");
	}
	
	/**
	 * 发送带参数的HTTP httpGet请求后按指定的字符集编码格式返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, String parameters, String encoding) throws IOException {
		AssertUtils.assertTrue(StringUtils.isNotEmpty(url), "URL address can not be null or empty.");
		BufferedReader reader = null;
		try {
			URL request = new URL(url + StringUtils.safeString(parameters));
			reader = IOUtils.newBufferedReader(request.openStream(), encoding);
			String response = IOUtils.read(reader);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(reader);
		}
		return null;
	}
	
	/** 
	 * 发送带参数的HTTP httpGet请求后按指定的字符集编码格式返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static <V> String httpGet(String url, Map<String, V> parameters, String encoding) throws IOException {
		return httpGet(url, MapUtils.joinQueryString(parameters), encoding);
	}
	
	/**
	 * 发送HTTP httpPost请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url) throws IOException {
		return httpPost(url, "", "");
	}
	
	/**
	 * 发送带HTTP httpPost请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, String parameters) throws IOException {
		return httpPost(url, parameters, "");
	}
	
	/**
	 * 发送带HTTP httpPost请求后返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static <V> String httpPost(String url, Map<String, V> parameters) throws IOException {
		return httpPost(url, MapUtils.joinQueryString(parameters), "");
	}
	
	/**
	 * 发送带参数的HTTP httpPost请求后按指定的字符集编码格式返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, String parameters, String encoding) throws IOException {
		AssertUtils.assertTrue(StringUtils.isNotEmpty(url), "URL address can not be null or empty.");
		HttpURLConnection connection = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try { 
			URL request = new URL(url);
			connection = (HttpURLConnection) request.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			
			/* 输入不为空的请求参数 */
			if (StringUtils.isNotEmpty(parameters)) {
				writer = IOUtils.newBufferedWriter(connection.getOutputStream(), encoding);
				IOUtils.write(writer, parameters);
			}
			reader = IOUtils.newBufferedReader(connection.getInputStream(), encoding);
			String response = IOUtils.read(reader);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
			IOUtils.close(reader);
			if (connection != null)
				connection.disconnect();
		}
		return null; 
	}
	
	/**
	 * 发送带参数的HTTP httpPost请求后按指定的字符集编码格式返回响应结果
	 * @author Daniele 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static <V> String httpPost(String url, Map<String, V> parameters, String encoding) throws IOException {
		return httpPost(url, MapUtils.joinQueryString(parameters), encoding);
	}
	
	/**
	 * 获取URL中的请求地址，即查询字符串之前的部分
	 * @author Daniele 
	 * @param url
	 * @return
	 */
	public static String getAddress(String url) {
		if (StringUtils.isBlank(url))
			return StringUtils.EMPTY;
		
		url = url.trim();
		int index = url.indexOf("?");
		if (index < 0) 
			return url;
		
		return index > -1 ? url.substring(0, index) : StringUtils.EMPTY;
	}
	
	/**
	 * 从URL的请求地址中获取以默认前缀和后缀标识的参数名称集
	 * @author Daniele 
	 * @param url
	 * @return
	 */
	public static Set<String> getAddressParameterNames(String url) {
		return getAddressParameterNames(url, "{", "}");
	}
	
	/**
	 * 从URL的Action字符串中获取参数名称集
	 * @author Daniele 
	 * @param url
	 * @param prefix 参数标识前缀
	 * @param suffix 参数标识后缀
	 * @return
	 */
	public static Set<String> getAddressParameterNames(String url, String prefix, String suffix) {
		Set<String> names = CollectionUtils.newLinkedHashSet();
		
		String address = getAddress(url);
		if (address.length() > 0) 
			names.addAll(StringUtils.leftSubstringAll(url, prefix, suffix));
		return names;
	}
	
	/**
	 * 获取URL包含的查询字符串
	 * @author Daniele 
	 * @param url
	 * @return
	 */
	public static String getQueryString(String url) {
		if (StringUtils.isBlank(url))
			return StringUtils.EMPTY;
		
		int index = url.indexOf("?");
		if (index < 0)
			return RegexUtils.isURLQueryString(url) ? url : StringUtils.EMPTY;
		
		String queryString = StringUtils.leftTrim(url.substring(index + 1));
		if (queryString.length() > 0 && queryString.startsWith("?")) 
			// 清除多余的?
			queryString = queryString.replaceFirst("[\\?]+", "");
		
		return queryString;
	}
		
	/**
	 * 从URL字符串中提取出所有参数名-值映射集
	 * @author Daniele 
	 * @param url
	 * @return
	 */
	public static Map<String, String> getParameterMap(String url) {
		String queryString = getQueryString(url);
		if (queryString.length() > 0) {
			Map<String, String> parameterMap = MapUtils.newHashMap();
			StringTokenizer tokenizer = new StringTokenizer(queryString, "&");
			String nameValuePair = null;
			while (tokenizer.hasMoreElements()) {
				nameValuePair = tokenizer.nextToken();
				parameterMap.put(StringUtils.beforeFrist(nameValuePair, "="), 
						StringUtils.afterFrist(nameValuePair, "="));
			}
			return parameterMap;
		}
		return null;
	}
	
	/**
	 * 判断端口号是否有效
	 * @author Daniele 
	 * @param port
	 * @return
	 */
	public static boolean isValidPort(int port) {
		return port >= MIN_PORT && port <= MAX_PORT;
	}
	
	/**
	 * 根据协议、主机地址和端口号拼接转换成URL
	 * @author Daniele 
	 * @param protocol
	 * @param host
	 * @param port
	 * @return
	 */
	public static String toURL(String protocol, String host, int port) {
		AssertUtils.assertNotBlank(protocol, "URL protocol must not be null or blank");
		AssertUtils.assertNotBlank(host, "URL host must not be null or blank");
		
		StringBuffer url = new StringBuffer(protocol);
		if (!protocol.endsWith("://"))
			url.append("://");
		url.append(host);
		
		if (StringUtils.startsWithIgnoreCase(protocol, HTTPS_PROTOCOL)) {
			if (isValidPort(port) && port != DEFAULT_HTTPS_PORT)
				url.append(":").append(port);
		} else if (StringUtils.startsWithIgnoreCase(protocol, HTTP_PROTOCOL)) {
			if (isValidPort(port) && port != DEFAULT_HTTP_PORT)
				url.append(":").append(port);
		} else 
			url.append(":").append(port);
		
		return url.toString();
	}
	
	/**
	 * 获取本机MAC地址
	 * @author Daniele 
	 * @return
	 */
	public static String getLocalMacAddress() {
		try {
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			return CodecUtils.bytesToHex(mac);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取本机全大写的MAC地址
	 * @author Daniele 
	 * @return
	 */
	public static String getLocalUpperCaseMacAddress() {
		String macAddress = getLocalMacAddress();
		return macAddress != null ? macAddress.toUpperCase() : macAddress;
	}
	
	/**
	 * 获取本机格式化的MAC地址
	 * @author Daniele 
	 * @return
	 */
	public static String getLocalFormatMacAddress() {
		try {
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				if (i > 0) 
					builder.append("-");
				
				int temp = mac[i]&0xff;
				String hex = Integer.toHexString(temp);
				if (hex.length() == 1)
					builder.append("0").append(hex);
				else
					builder.append(hex);
			}
			return builder.toString().toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}
					
}
