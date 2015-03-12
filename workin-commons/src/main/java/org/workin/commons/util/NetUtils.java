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

/**
 * @description 网络通信工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NetUtils {
	
	/** 默认超时3000毫秒 */
	public static final int DEFAULT_TIMEOUT = 3000;
	
	/** 默认80端口 */
	public static final int DEFAULT_PORT = 80;
	
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
		return httpGet(url, MapUtils.joinURLParameters(parameters), "");
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
		return httpGet(url, MapUtils.joinURLParameters(parameters), encoding);
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
		return httpPost(url, MapUtils.joinURLParameters(parameters), "");
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
		return httpPost(url, MapUtils.joinURLParameters(parameters), encoding);
	}
		
}
