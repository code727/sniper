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

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.workin.commons.util.NumberUtils;
import org.workin.kafka.producer.KafkaProducer;
import org.workin.test.spring.JUnit4SpringTestCase;

/**
 * 生产者测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext-producer.xml" })
public class ProducerTest extends JUnit4SpringTestCase {
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
//	@Test
	public void testSendDefault() {
		Message message = new Message(NumberUtils.randomIn(10000), "testSendDefault", new Date());
		kafkaProducer.sendDefault(message.getId(), message);
	}
	
	@Test
	public void testSend() throws Exception {
		try {
			Message message = new Message(NumberUtils.randomIn(10000), "testSend", new Date());
			final ProducerRecord<Integer, Message> producerRecord = new ProducerRecord<Integer, Message>("test", message.getId(), message);
			kafkaProducer.send(producerRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
		
}
