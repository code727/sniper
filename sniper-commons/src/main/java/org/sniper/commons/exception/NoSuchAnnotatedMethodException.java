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

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.StringUtils;

/**
 * "未匹配有已注解的方法"异常
 * @author  Daniele
 * @version 1.0
 */
public class NoSuchAnnotatedMethodException extends NoSuchMethodException {

	private static final long serialVersionUID = 431347751345676377L;
	
	public <A extends Annotation> NoSuchAnnotatedMethodException(Object obj, Class<A> annotationClass) {
		super(buildMessage(obj, annotationClass));
	}
	
	/**
	 * 构建异常消息
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	private static <A extends Annotation> String buildMessage(Object obj, Class<A> annotationClass) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		return String.format("{\"currentType\":%s,\"annotationClass\":%s}", 
				(currentType != null ? StringUtils.appendDoubleQuotes(currentType.getName()) : currentType),
				(annotationClass != null ? StringUtils.appendDoubleQuotes(annotationClass.getName()) : annotationClass));
	}
	
}
