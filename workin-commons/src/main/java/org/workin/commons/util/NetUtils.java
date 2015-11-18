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

package org.workin.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @description 网络通信工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NetUtils {
	
	/** HTTP协议 */
	public static final String HTTP_PROTOCOL = "http";
	
	/** HTTPS协议 */
	public static final String HTTPS_PROTOCOL = "https";
	
	/** 默认HTTP协议80端口 */
	public static final int DEFAULT_HTTP_PORT = 80;
	
	/** 默认HTTPS协议443端口 */
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
	 * @description 获取本机的IP地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws UnknownHostException 
	 * @throws Exception
	 */
	public static String getHostIp() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostAddress();
	}
	
	/**
	 * @description 获取本机的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostName();
	}
	
	/**
	 * @description 发送HTTP httpGet请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url) throws IOException {
		return httpGet(url, "", "");
	}
	
	/**
	 * @description 发送带参数的HTTP httpGet请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, String parameters) throws IOException {
		return httpGet(url, parameters, "");
	}
	
	/**
	 * @description 发送带参数的HTTP httpGet请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, Map<String, String> parameters) throws IOException {
		return httpGet(url, MapUtils.joinQueryString(parameters), "");
	}
	
	/**
	 * @description 发送带参数的HTTP httpGet请求后按指定的字符集编码格式返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @description 发送带参数的HTTP httpGet请求后按指定的字符集编码格式返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url, Map<String,String> parameters, String encoding) throws IOException {
		return httpGet(url, MapUtils.joinQueryString(parameters), encoding);
	}
	
	/**
	 * @description 发送HTTP httpPost请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url) throws IOException {
		return httpPost(url, "", "");
	}
	
	/**
	 * @description 发送带HTTP httpPost请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, String parameters) throws IOException {
		return httpPost(url, parameters, "");
	}
	
	/**
	 * @description 发送带HTTP httpPost请求后返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, Map<String, String> parameters) throws IOException {
		return httpPost(url, MapUtils.joinQueryString(parameters), "");
	}
	
	/**
	 * @description 发送带参数的HTTP httpPost请求后按指定的字符集编码格式返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @description 发送带参数的HTTP httpPost请求后按指定的字符集编码格式返回响应结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url, Map<String,String> parameters, String encoding) throws IOException {
		return httpPost(url, MapUtils.joinQueryString(parameters), encoding);
	}
	
	/**
	 * @description  获取URL中包含协议、主机域/IP和端口号在内的Action字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	public static String getActionString(String url) {
		if (StringUtils.isBlank(url))
			return StringUtils.EMPTY_STRING;
		
		url = url.trim();
		int index = url.indexOf("?");
		if (index < 0) 
			return url;
		
		return index > -1 ? url.substring(0, index) : StringUtils.EMPTY_STRING;
	}
	
	/**
	 * @description 从URL的Action字符串中获取以默认前缀和后缀标识的参数名称集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	public static Set<String> getActionParameterNames(String url) {
		return getActionParameterNames(url, "{", "}");
	}
	
	/**
	 * @description 从URL的Action字符串中获取参数名称集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param prefix 参数标识前缀
	 * @param suffix 参数标识后缀
	 * @return
	 */
	public static Set<String> getActionParameterNames(String url, String prefix, String suffix) {
		Set<String> names = CollectionUtils.newHashSet();
		String actionString = getActionString(url);
		if (actionString.length() > 0) 
			names.addAll(CollectionUtils.newHashSet(StringUtils.leftSubstringAll(url, prefix, suffix)));
		return names;
	}
	
	/**
	 * @description 获取URL包含的查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	public static String getQueryString(String url) {
		int index;
		if (StringUtils.isBlank(url) || (index = url.indexOf("?")) < 0)
			return StringUtils.EMPTY_STRING;
		
		String queryString = StringUtils.leftTrim(url.substring(index + 1));
		if (queryString.length() > 0 && queryString.startsWith("?")) 
			// 清除多余的?
			queryString = queryString.replaceFirst("[\\?]+", "");
		
		return queryString;
	}
	
	/**
	 * @description 从URL字符串中提取出所有参数名-值映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @description 判断端口号是否有效
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param port
	 * @return
	 */
	public static boolean isValidPort(int port) {
		return port >= MIN_PORT && port <= MAX_PORT;
	}
	
	/**
	 * @description 根据协议、主机地址和端口号拼接转换成URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param protocol
	 * @param host
	 * @param port
	 * @return
	 */
	public static String toURL(String protocol, String host, int port) {
		protocol = StringUtils.trimToEmpty(protocol);
		host = StringUtils.trimToEmpty(host);
		AssertUtils.assertTrue(protocol.length() > 0, "URL protocol can not be null or empty.");
		AssertUtils.assertTrue(host.length() > 0, "URL host can not be null or empty.");
		
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
				
}
