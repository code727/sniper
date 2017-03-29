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
 * Topic管理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TopicManagerImpl implements TopicManager {
	
	private Map<String, TopicNode> topicNodes;

	@Override
	public void setTopics(Map<String, TopicNode> topicNodes) {
		this.topicNodes = topicNodes;
	}

	@Override
	public Map<String, TopicNode> getTopicNodes() {
		return topicNodes;
	}

	@Override
	public TopicNode getTopicNode(String name) {
		return topicNodes.get(name);
	}

}
