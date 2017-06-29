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

package org.sniper.http;

import java.util.Date;
import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP请求头实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimpleHttpRequestHeader implements HttpRequestHeader {
	
	/** 名-值对映射集 */
	private Map<String, Object> attributes = MapUtils.newConcurrentHashMap();

	@Override
	public void setAttribute(String name, Object value) {
		if (attributes == null)
			attributes = MapUtils.newConcurrentHashMap();
		
		attributes.put(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		return this.getAttribute(name, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String name, Class<T> clazz) {
		AssertUtils.assertNotNull(clazz, "Attribute value type can not be null.");
		return (T) (attributes != null ? attributes.get(name) : null);
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = MapUtils.newConcurrentHashMap(attributes);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public void removeAttribute(String name) {
		this.attributes.remove(name);
	}

	@Override
	public void removeAll() {
		this.attributes.clear();
	}

	@Override
	public void setAccept(String mediaTypes) {
		this.setAttribute(ACCEPT, mediaTypes);
	}

	@Override
	public String getAccept() {
		return this.getAttribute(ACCEPT, String.class);
	}

	@Override
	public void setAcceptCharset(String acceptCharset) {
		this.setAttribute(ACCEPT_CHARSET, acceptCharset);
	}

	@Override
	public String getAcceptCharset() {
		return this.getAttribute(ACCEPT_CHARSET, String.class);
	}

	@Override
	public void setAcceptEncoding(String acceptEncoding) {
		this.setAttribute(ACCEPT_ENCODING, acceptEncoding);
	}

	@Override
	public String getAcceptEncoding() {
		return this.getAttribute(ACCEPT_ENCODING, String.class);
	}

	@Override
	public void setAcceptLanguage(String acceptLanguage) {
		this.setAttribute(ACCEPT_LANGUAGE, acceptLanguage);
	}

	@Override
	public String getAcceptLanguage() {
		return this.getAttribute(ACCEPT_LANGUAGE, String.class);
	}

	@Override
	public void setAuthorization(String authorization) {
		this.setAttribute(AUTHORIZATION, authorization);
	}

	@Override
	public String getAuthorization() {
		return this.getAttribute(AUTHORIZATION, String.class);
	}

	@Override
	public void setConnection(String connection) {
		this.setAttribute(CONNECTION, connection);
	}

	@Override
	public String getConnection() {
		return this.getAttribute(CONNECTION, String.class);
	}

	@Override
	public void setContentLength(String contentLength) {
		this.setAttribute(CONTENT_LENGTH, contentLength);
	}

	@Override
	public String getContentLength() {
		return this.getAttribute(CONTENT_LENGTH, String.class);
	}

	@Override
	public void setCookie(String cookie) {
		this.setAttribute(COOKIE, cookie);
	}

	@Override
	public String getCookie() {
		return this.getAttribute(COOKIE, String.class);
	}

	@Override
	public void setFrom(String from) {
		this.setAttribute(FROM, from);
	}

	@Override
	public String getFrom() {
		return this.getAttribute(FROM, String.class);
	}

	@Override
	public void setHost(String host) {
		this.setAttribute(HOST, host);
	}

	@Override
	public String getHost() {
		return this.getAttribute(HOST, String.class);
	}

	@Override
	public void setIfModifiedSince(Date date) {
		this.setAttribute(IF_MODIFIED_SINCE, date);
	}

	@Override
	public Date getIfModifiedSince() {
		return this.getAttribute(IF_MODIFIED_SINCE, Date.class);
	}

	@Override
	public void setPragma(String pragma) {
		this.setAttribute(PRAGMA, pragma);
	}

	@Override
	public String getPragma() {
		return this.getAttribute(PRAGMA, String.class);
	}

	@Override
	public void setReferer(String referer) {
		this.setAttribute(REFERER, referer);
	}

	@Override
	public String getReferer() {
		return this.getAttribute(REFERER, String.class);
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.setAttribute(USER_AGENT, userAgent);
	}

	@Override
	public String getUserAgent() {
		return this.getAttribute(USER_AGENT, String.class);
	}

}
