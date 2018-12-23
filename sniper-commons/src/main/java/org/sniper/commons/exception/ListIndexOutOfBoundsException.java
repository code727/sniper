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
 * Create Date : 2018-12-20
 */

package org.sniper.commons.exception;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;

/**
 * 列表下标越界异常
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ListIndexOutOfBoundsException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 5732493177515672648L;
	
	public ListIndexOutOfBoundsException() {
		super();
	}
	
	public ListIndexOutOfBoundsException(String message) {
		super(message);
	}
	
	public ListIndexOutOfBoundsException(int index) {
		 super(String.format("List index out of range:%d", index));
	}
	
	public ListIndexOutOfBoundsException(int index, List<?> list) {
		super(String.format("List index out of range:%d,size:%d", index, CollectionUtils.size(list)));
	}
	
}
