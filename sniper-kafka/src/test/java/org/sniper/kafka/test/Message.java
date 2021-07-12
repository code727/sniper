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
 * Create Date : 2017年3月9日
 */

package org.sniper.kafka.test;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试用的消息实例
 * @author  Daniele
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Message implements Serializable {
	
	private int id;
	
	private String text;
	
	private Date createTime;
	
	public Message() {
		
	}
	
	public Message(int id, String text, Date createTime) {
		this.id = id;
		this.text = text;
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
