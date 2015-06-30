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
 * Create Date : 2015-6-29
 */

package org.workin.trace.advice;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.commons.util.DateUtils;
import org.workin.spring.aop.MatchableMethodAroundAdvice;
import org.workin.support.context.ApplicationContextHolder;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.service.BehaviorPerformanceLoggerService;
import org.workin.trace.service.BehaviorPerformanceService;

/**
 * @description 性能采集拦截切面实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BehaviorPerformanceAdvice extends MatchableMethodAroundAdvice {
	
	private static final String BEHAVIOR_PERFORMANCE = "behavior_performance";
	
	private Logger logger = LoggerFactory.getLogger(BehaviorPerformanceAdvice.class);
	
	private BehaviorPerformanceService behaviorPerformanceService;
	
	public void setBehaviorPerformanceService(
			BehaviorPerformanceService behaviorPerformanceService) {
		this.behaviorPerformanceService = behaviorPerformanceService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (behaviorPerformanceService == null) {
			behaviorPerformanceService = new BehaviorPerformanceLoggerService();
			logger.info("behaviorPerformanceService is null, use default implementation:"
					+ BehaviorPerformanceLoggerService.class.getName());
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	protected void doBeforeTask(Method method, Object[] args, Object target) {
		
		if (ApplicationContextHolder.getAttribute(BEHAVIOR_PERFORMANCE) == null)
			ApplicationContextHolder.setAttribute(BEHAVIOR_PERFORMANCE, new Stack<BehaviorPerformance>());
		
		BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
		behaviorPerformance.setMethod(method);
		behaviorPerformance.setDeclaringClass(method.getDeclaringClass().getName());
		behaviorPerformance.setMethodName(method.getName());
		behaviorPerformance.setStartTime(new Date());
		
		((Stack<BehaviorPerformance>) ApplicationContextHolder
				.getAttribute(BEHAVIOR_PERFORMANCE)).add(behaviorPerformance);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doAfterReturningTask(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		// 从堆栈里取出最近一个被doBeforeTask()处理的方法BehaviorPerformance对象，可保证总是先得到最里层的方法
		BehaviorPerformance behaviorPerformance = ((Stack<BehaviorPerformance>) ApplicationContextHolder
				.getAttribute(BEHAVIOR_PERFORMANCE)).pop();
		
		if (method == behaviorPerformance.getMethod()) {
			behaviorPerformance.setEndTime(new Date());
			behaviorPerformance.setElapsedTime(DateUtils.getIntervalMillis(
					behaviorPerformance.getEndTime(), behaviorPerformance.getStartTime()));
			behaviorPerformanceService.store(behaviorPerformance);
		}
	}

}
