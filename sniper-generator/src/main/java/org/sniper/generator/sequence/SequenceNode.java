/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-6-29
 */

package org.sniper.generator.sequence;

import java.io.Serializable;

/**
 * 序列节点对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SequenceNode implements Serializable {

	private static final long serialVersionUID = 7509793828337838376L;
	
	 /** 服务器ID */
    private long workerId;

    /** 数据中心ID*/
    private long dataCenterId;
    
    public SequenceNode() {
    	this(0, 0);
    }
    
    public SequenceNode(long workerId, long dataCenterId) {
    	this.workerId = workerId;
    	this.dataCenterId = dataCenterId;
    }
    
	public long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	public long getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(long dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

}
