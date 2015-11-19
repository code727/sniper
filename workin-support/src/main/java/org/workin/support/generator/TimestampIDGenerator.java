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
 * Create Date : 2015-11-19
 */

package org.workin.support.generator;

import java.util.Date;

import org.workin.commons.util.DateUtils;
import org.workin.support.generator.adapter.MinLengthIDGeneratorAdapter;

/**
 * @description 时间戳序列ID生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TimestampIDGenerator extends MinLengthIDGeneratorAdapter {
	
	/** 序列格式 */
	private String format = "yyyyMMddHHmmssSSS";
	
	/** 是否附带上纳秒级时间 */
	private boolean attachNanoTime;
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isAttachNanoTime() {
		return attachNanoTime;
	}

	public void setAttachNanoTime(boolean attachNanoTime) {
		this.attachNanoTime = attachNanoTime;
	}

	@Override
	protected String generateBody() {
		if (isAttachNanoTime()) {
			long nanoTime = System.nanoTime(); 
			String body = DateUtils.dateToString(new Date(), this.format);
			// 附带上纳秒级的时间间隔
			return body + (System.nanoTime() - nanoTime);
		}
		return DateUtils.dateToString(new Date(), this.format);
	}
	
}
