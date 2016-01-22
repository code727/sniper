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
 * Create Date : 2015-6-19
 */

package org.workin.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.FileUtils;
import org.workin.commons.util.IOUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description Web工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WebUtils {
	
	/** HttpServletRequest对象的属性名 */
	public static final String HTTP_SERVLET_REQUEST_NAME = HttpServletRequest.class.getName();
	
	/** HttpServletResponse对象的属性名 */
	public static final String HTTP_SERVLET_RESPONSE_NAME = HttpServletResponse.class.getName();
	
	/** HttpSession对象的属性名 */
	public static final String HTTP_SESSION_NAME = HttpSession.class.getName();
	
	/**
	 * @description 设置HttpServletRequest对象的字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param encoding
	 * @throws UnsupportedEncodingException
	 */
	public static void setCharacterEncoding(HttpServletRequest request, String encoding)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(encoding))
			request.setCharacterEncoding(encoding);
		else
			request.setCharacterEncoding(CodecUtils.UTF8_ENCODING);
	}
	
	/**
	 * @description 设置HttpServletRequest对象的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 * @param value
	 */
	public static void setAttribute(HttpServletRequest request, String name, Object value) {
		if (name != null)
			request.setAttribute(name, value);
	}
	
	/**
	 * @description 设置HttpSession对象的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param name
	 * @param value
	 */
	public static void setAttribute(HttpSession session, String name, Object value) {
		if (name != null)
			session.setAttribute(name, value);
	}
	
	/**
	 * @description 设置当前会话在多少秒无操作后处于失效状态
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param sec
	 */
	public static void setMaxInactiveInterval(HttpSession session, int sec) {
		session.setMaxInactiveInterval(sec);
	}
			
	/**
	 * @description 获取HttpServletRequest对象里的参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, null);
	}
	
	/**
	 * @description 获取HttpServletRequest对象里的参数值，为空时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
		String value = request.getParameter(name);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取ServletRequest对象里所有的参数"名-值"对映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> getParameterMap(ServletRequest request) {
		return request.getParameterMap();
	}
	
	/**
	 * @description 获取ServletRequest对象里所有的参数"名-值"字符串对映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameters(ServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> parameters = MapUtils.newHashMap();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			parameters.put(name, ArrayUtils.get(request.getParameterValues(name), 0));
		}
		return parameters;
	}
	
	/**
	 * @description 获取HttpServletRequest对象里的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 * @return
	 */
	public static <T> T getAttribute(HttpServletRequest request, String name) {
		return getAttribute(request, name, null);
	}
	
	/**
	 * @description 获取HttpServletRequest对象里的属性值，为空时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpServletRequest request, String name, T defaultValue) {
		T value = (T) request.getAttribute(name);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取HttpSession对象里的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param name
	 * @return
	 */
	public static <T> T getAttribute(HttpSession session, String name) {
		return getAttribute(session, name, null);
	}
	
	/**
	 * @description 获取HttpSession对象里的属性值，为空时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpSession session, String name, T defaultValue) {
		T value = (T) session.getAttribute(name);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取HttpSession对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return getSession(request, false);
	}
	
	/**
	 * @description 获取HttpSession对象，为空时选择是否自动创建一个新对象后返回。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param create
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request, boolean create) {
		return request.getSession(create);
	}
	
	/**
	 * @description 删除HttpServletRequest对象里的指定属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param name
	 */
	public static void removeAttribute(HttpServletRequest request, String name) {
		request.removeAttribute(name);
	}
	
	/**
	 * @description 删除HttpSession对象里指定的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param name
	 */
	public static void removeAttribute(HttpSession session, String name) {
		session.removeAttribute(name);
	}
	
	/**
	 * @description 删除HttpServletRequest对象里所有的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static void removeAll(HttpServletRequest request) {
		Enumeration<String> names = request.getAttributeNames();
		while (names.hasMoreElements()) {
			request.removeAttribute(names.nextElement());
		}
	}
	
	/**
	 * @description 删除HttpSession对象里所有的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public static void removeAll(HttpSession session) {
		Enumeration<String> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			session.removeAttribute(names.nextElement());
		}
	}
	
	/**
	 * @description 删除HttpServletRequest以及关联的HttpSession对象里所有的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param session
	 */
	public static void clearAll(HttpServletRequest request) {
		clearAll(request, null);
	}
	
	/**
	 * @description 同时删除HttpServletRequest和HttpSession对象里所有的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param session
	 */
	public static void clearAll(HttpServletRequest request, HttpSession session) {
		if (session == null) {
			HttpSession correlative_session = request.getSession(false);
			if (correlative_session != null)
				removeAll(correlative_session);
		} else
			removeAll(session);
		removeAll(request);
	}
	
	/**
	 * @description 获取客户端不带参数的请求地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static StringBuffer getRequestURL(HttpServletRequest request) {
		return request.getRequestURL();
	}
	
	/**
	 * @description 获取客户端查询参数部分的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request) {
		return request.getQueryString();
	}
	
	/**
	 * @description 获取客户端带查询参数完整的请求地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static String getIntegralRequestURL(HttpServletRequest request) {
		String queryString = getQueryString(request);
		return StringUtils.isNotBlank(queryString) ? 
				getRequestURL(request).append("?").append(queryString).toString() : 
					getRequestURL(request).toString();
	}
	
	/**
	 * @description 获取Servlet路径(相对于当前工程下资源的路径)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static String getServletPath(HttpServletRequest request) {
		return request.getServletPath();
	}
	
	/**
	 * @description 根据HttpServletRequest对象的头信息进行字符串编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(HttpServletRequest request, String str) throws UnsupportedEncodingException {
		String agent = request.getHeader("USER-AGENT");    
		if (agent != null && agent.indexOf("MSIE") > -1)
			return URLEncoder.encode(str, CodecUtils.UTF8_ENCODING);
		else
			return new String(str.getBytes(CodecUtils.UTF8_ENCODING), CodecUtils.ISO_8859_1);
	}
	
	/**
	 * @description 下载指定URL目标的资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
		download(url, null, request, response);
	}
	
	/**
	 * @description 下载指定URL目标的资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param attachmentName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(String url, String attachmentName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AssertUtils.assertTrue(StringUtils.isNotBlank(url), "URL must not be null or blank.");
		
		// 如果传入的附件名为空，则从URL路径中获取
		if (StringUtils.isBlank(attachmentName))
			attachmentName = FileUtils.getName(url);
		
		URL target = new URL(url);
		URLConnection conn = target.openConnection();  
		download(conn.getInputStream(), attachmentName, request, response);
	}
	
	/**
	 * @description 下载InputStream资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(InputStream in, HttpServletRequest request, HttpServletResponse response) throws IOException {
		download(in, null, request, response);
	}
	
	/**
	 * @description 下载InputStream资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param attachmentName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(InputStream in, String attachmentName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AssertUtils.assertNotNull(in, "InputStream object must not be null.");
		AssertUtils.assertNotNull(request, "HttpServletRequest object must not be null.");
		AssertUtils.assertNotNull(response, "HttpServletResponse object must not be null.");
		
		if (StringUtils.isNotEmpty(attachmentName))
			response.addHeader("Content-Disposition", "attachment;filename="
					+ WebUtils.encode(request, attachmentName));
		
		String contentType = FileUtils.getExtensionName(attachmentName);
		if (StringUtils.isNotEmpty(contentType))
			response.setContentType("application/" + contentType);
		
		byte[] bytes = new byte[IOUtils.DEFAULT_BUFFER_SIZE];
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = IOUtils.newBufferedInputStream(in);
			output = IOUtils.newBufferedOutputStream(response.getOutputStream());
			int i = 0;
			/* 按块写入 */
			while ((i = in.read(bytes)) > 0) 
				output.write(bytes, 0, i);
			output.flush();
		} finally {
			IOUtils.close(output);
			IOUtils.close(input);
		}
	}
	
	/**
	 * @description 下载字节数组资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(byte[] bytes, HttpServletRequest request, HttpServletResponse response) throws IOException {
		download(bytes, null, request, response);
	}
	
	/**
	 * @description 下载字节数组资源，写入HttpServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @param attachmentName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void download(byte[] bytes, String attachmentName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(bytes), "Downloaded source bytes must not be null or empty.");
		AssertUtils.assertNotNull(request, "HttpServletRequest object must not be null.");
		AssertUtils.assertNotNull(response, "HttpServletResponse object must not be null.");
		
		if (StringUtils.isNotEmpty(attachmentName))
			response.addHeader("Content-Disposition", "attachment;filename="
					+ WebUtils.encode(request, attachmentName));
		
		String contentType = FileUtils.getExtensionName(attachmentName);
		if (StringUtils.isNotEmpty(contentType))
			response.setContentType("application/" + contentType);
		
		BufferedOutputStream out = null;
		try {
			out = IOUtils.newBufferedOutputStream(response.getOutputStream());
			out.write(bytes);
			out.flush();
		} finally {
			IOUtils.close(out);
		}
	}
	
}
