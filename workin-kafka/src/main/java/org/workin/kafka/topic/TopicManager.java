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
 * Create Date : 2017-3-7
 */

package org.workin.kafka.topic;

import java.util.Map;

/**
 * Topic管理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface TopicManager {
	
	/**
	 * 设置Topic映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNodes
	 */
	public void setTopics(Map<String, TopicNode> topicNodes);
	
	/**
	 * 获取Topic映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, TopicNode> getTopicNodes();
	
	/**
	 * 根据键名称获取Topic实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey
	 * @return
	 */
	public TopicNode getTopicNode(String topicKey);
	
}
