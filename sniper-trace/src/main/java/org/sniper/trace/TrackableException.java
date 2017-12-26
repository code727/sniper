/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-12-26
 */

package org.sniper.trace;

import org.sniper.commons.exception.SniperException;
import org.sniper.commons.util.StringUtils;

/**
 * 可跟踪的异常
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TrackableException extends SniperException {

	private static final long serialVersionUID = -7244745075812853962L;
	
	/** 自定义异常编码 */
	protected final String code;
	
	public TrackableException() {
		this((String) null);
	}
	
	public TrackableException(String code) {
		this(code, (String) null);
	}
	
	public TrackableException(Throwable throwable) {
		this(null, throwable);
	}
	
	public TrackableException(String code, String message) {
		this(code, message, null);
	}
		
	public TrackableException(String code, Throwable throwable) {
		this(code, null, throwable);
	}
	
	public TrackableException(String code, String messgae, Throwable throwable) {
		super(messgae, throwable);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	/**
	 * 获取可跟踪的异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getTrackableMessage() {
		String message = getLocalizedMessage();
		if (StringUtils.isNotBlank(code))
			return StringUtils.isNotBlank(message) ? (code + "-" + message) : code;
			
		return StringUtils.isNotBlank(message) ? message : StringUtils.EMPTY;
	}
	
	@Override
	public String toString() {
		String s = getClass().getName();
		String trackableMessage = getTrackableMessage();
		return (StringUtils.isNotBlank(trackableMessage)) ? (s + ": " + trackableMessage) : s;
	}
	
}
