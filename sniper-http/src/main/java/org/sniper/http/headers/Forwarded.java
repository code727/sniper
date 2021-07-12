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
 * Create Date : 2017年9月7日
 */

package org.sniper.http.headers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.sniper.commons.enums.http.HttpProtocolEnum;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * @author  Daniele
 * @version 1.0
 */
public class Forwarded implements Serializable {

	private static final long serialVersionUID = -2787790431971634392L;
	
	private String by;
	
	private List<String> multiFor;
	
	private String host;
	
	private HttpProtocolEnum protocol;
	
	public Forwarded() {
		this.multiFor = CollectionUtils.newArrayList();
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		if (StringUtils.isNotBlank(by))
			this.by = by;
	}

	public List<String> getMultiFor() {
		return multiFor;
	}

	public void setMultiFor(List<String> multiFor) {
		if (CollectionUtils.isNotEmpty(multiFor)) {
			for (String xFor : multiFor) {
				addFor(xFor);
			}
		}
	}
	
	public void addFor(String xFor) {
		if (StringUtils.isNotBlank(xFor)) {
			this.multiFor.add(xFor);
		}
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (StringUtils.isNotBlank(host)) {
			this.host = host;
		}
	}

	public HttpProtocolEnum getProtocol() {
		return protocol;
	}

	public void setProtocol(HttpProtocolEnum protocol) {
		this.protocol = protocol;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotBlank(by)) {
			builder.append("by=").append(by);
		}
		
		if (CollectionUtils.isNotEmpty(multiFor)) {
			if (builder.length() > 0) {
				builder.append(HttpHeaders.NAME_VALUE_PAIR_SEPARATOR);
			}
				
			
			Iterator<String> iterator = multiFor.iterator();
			while (iterator.hasNext()) {
				builder.append("for=").append(iterator.next());
				if (iterator.hasNext()) {
					builder.append(HttpHeaders.VALUE_SEPARATOR);
				}
			}
		}
		
		if (StringUtils.isNotBlank(host)) {
			if (builder.length() > 0) {
				builder.append(HttpHeaders.NAME_VALUE_PAIR_SEPARATOR);
			}
			
			builder.append("host=").append(host);
		}
		
		if (protocol != null) {
			if (builder.length() > 0) {
				builder.append(HttpHeaders.NAME_VALUE_PAIR_SEPARATOR);
			}
				
			builder.append("proto=").append(protocol.name().toLowerCase());
		}
			
		return builder.toString();
	}	
	
}
