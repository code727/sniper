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
 * Create Date : 2018-12-11
 */

package org.sniper.commons.exception;

import java.lang.annotation.Annotation;

/**
 * "未匹配有已注解的Setter方法"异常
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NoSuchAnnotatedSetterException extends NoSuchAnnotatedMethodException {
	
	private static final long serialVersionUID = 2900985401578448318L;

	public <A extends Annotation> NoSuchAnnotatedSetterException(Object obj, Class<A> annotationClass) {
		super(obj, annotationClass);
	}

}
