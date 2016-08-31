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

package org.workin.trace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.commons.util.DateUtils;
import org.workin.trace.domain.BehaviorPerformance;

/**
 * 性能采集日志服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BehaviorPerformanceLoggerService implements BehaviorPerformanceService {
	
	private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceLoggerService.class);
	
	private static final String message = new StringBuffer()
		.append("\n").append("--------------- Successful get behavior performance ---------------").append("\n")
		.append("declaringClass:{}").append("\n")
		.append("methodName:{}").append("\n")
		.append("startTime:{}").append("\n")
		.append("endTime:{}").append("\n")
		.append("elapsedTime:{}ms").append("\n")
		.toString();
			

	@Override
	public void store(BehaviorPerformance behaviorPerformance) {
		logger.info(message, behaviorPerformance.getDeclaringClass(), 
				behaviorPerformance.getMethodName(),
				DateUtils.dateToString(behaviorPerformance.getStartTime()),
				DateUtils.dateToString(behaviorPerformance.getEndTime()),
				behaviorPerformance.getElapsedTime());
	}

}
