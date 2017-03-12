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

package org.workin.kafka.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.workin.kafka.producer.KafkaProducer;
import org.workin.kafka.producer.callback.ProduceFutureCallback;
import org.workin.kafka.support.ProduceResult;
import org.workin.kafka.topic.Topic;
import org.workin.test.spring.JUnit4SpringTestCase;

/**
 * 生产者测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext-producer.xml" })
public class ProducerTest extends JUnit4SpringTestCase {
	
	@Autowired
	private KafkaProducer<Integer, Message> kafkaProducer;
	
//	@Test
	public void testSend() throws Exception {
		Message message = new Message(1025, "testSend", new Date());
		ProduceFutureCallback<Integer, Message> callback = new ProduceFutureCallback<Integer, Message>();
		kafkaProducer.send(message.getId(), message, callback);
	}
	
	@Test
	public void sendAndWait() throws Exception {
		Message message = new Message(4444, "kafka_sendAndWait", new Date());
		
		ProduceResult<Integer, Message> result = kafkaProducer.sendAndWait(message.getId(), message);
		Topic targetTopic = result.getTargetTopic();
		
		System.out.println("Sucess send to target topic [name:"
				+ targetTopic.getName() + ",partition:" + targetTopic.getPartition() + "]");
	}
		
}
