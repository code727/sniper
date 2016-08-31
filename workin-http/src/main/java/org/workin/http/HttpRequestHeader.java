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
 * Create Date : 2015-7-5
 */

package org.workin.http;

import java.util.Date;


/**
 * HTTP请求头接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpRequestHeader extends HttpHeader {
	
	/** 可接受的MIME类型 */
	public static final String ACCEPT = "Accept";
	
	/** 可接受的字符集 */
	public static final String ACCEPT_CHARSET = "Accept-Charset";
	
	/** 能够进行解码的数据编码方式 */
	public static final String ACCEPT_ENCODING = "Accept_Encoding";
	
	/** 浏览器所希望的语言种类 */
	public static final String ACCEPT_LANGUAGE = "Accept_Language";
	
	/** 授权信息 */
	public static final String AUTHORIZATION = "Authorization";
	
	/** 是否需要持久连接 */
	public static final String CONNECTION = "Connection";
	
	/** 消息正文长度 */
	public static final String CONTENT_LENGTH = "Content-Length";
	
	/** Cookie信息 */
	public static final String COOKIE = "Cookie";
	
	/** 请求发送者的email地址 */
	public static final String FROM = "From";
	
	/** 初始URL中的主机和端口 */
	public static final String HOST = "Host";
	
	/** 请求内容的修改日期 */
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	
	/** Pragma */
	public static final String PRAGMA = "Pragma";
	
	/** 包含一个URL，用户从该URL代表的页面出发访问当前请求的页面 */
	public static final String REFERER = "Referer";
	
	/** 浏览器类型 */
	public static final String USER_AGENT = "User-Agent";
	
	/**
	 * 设置Accept
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mediaTypes
	 */
	public void setAccept(String mediaTypes);
	
	/**
	 * 获取Accept
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAccept();
	
	/**
	 * 设置Accept-Charset
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param acceptCharset
	 */
	public void setAcceptCharset(String acceptCharset);
	
	/**
	 * 获取Accept-Charset
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAcceptCharset();
	
	/**
	 * 设置Accept-Encoding
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param acceptEncoding
	 */
	public void setAcceptEncoding(String acceptEncoding);
	
	/**
	 * 获取Accept-Encoding
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAcceptEncoding();
	
	/**
	 * 设置Accept-Language
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param acceptLanguage
	 */
	public void setAcceptLanguage(String acceptLanguage);
	
	/**
	 * 获取Accept-Language
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAcceptLanguage();	
	
	/**
	 * 设置Authorization
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param authorization
	 */
	public void setAuthorization(String authorization);
	
	/**
	 * 获取Authorization
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAuthorization();
	
	/**
	 * 设置Connection
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 */
	public void setConnection(String connection);
	
	/**
	 * 获取Connection
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getConnection();
	
	/**
	 * 设置Content-Length
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param contentLength
	 */
	public void setContentLength(String contentLength);
	
	/**
	 * 获取Content-Length
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getContentLength();
	
	/**
	 * 设置Cookie
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cookie
	 */
	public void setCookie(String cookie);
	
	/**
	 * 获取Cookie
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getCookie();

	/**
	 * 设置From
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param from
	 */
	public void setFrom(String from);
	
	/**
	 * 获取From
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getFrom();
	
	/**
	 * 设置Host
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param host
	 */
	public void setHost(String host);
	
	/**
	 * 获取Host
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getHost();
	
	/**
	 * 设置If-Modified-Since
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 */
	public void setIfModifiedSince(Date date);
	
	/**
	 * 获取If-Modified-Since
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Date getIfModifiedSince();
	
	/**
	 * 设置Pragma
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pragma
	 */
	public void setPragma(String pragma);
	
	/**
	 * 获取Pragma
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPragma();
	
	/**
	 * 设置Referer
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param referer
	 */
	public void setReferer(String referer);
	
	/**
	 * 获取Referer
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getReferer();
	
	/**
	 * 设置User-Agent
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent);
	
	/**
	 * 获取User-Agent
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public String getUserAgent();
	
}
