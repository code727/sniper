/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-12
 */

package org.workin.serialization.test.serializer.json;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Test;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.IOUtils;
import org.workin.serialization.json.proxy.JacksonSerializer;
import org.workin.serialization.test.serializer.AbstractSerializerTest;

/**
 * @description Jackson序列化器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JacksonSerializerTest extends AbstractSerializerTest {
	
	private JacksonSerializer jacksonSerializer = new JacksonSerializer();
	
	@Override
	@Test
	public void testSerialize() throws Exception {
		bytes = jacksonSerializer.serialize(list);
		
		String path = "C:/Users/Administrator/Desktop/jacksonSerializer.txt";
		IOUtils.write(new FileOutputStream(new File(path)), bytes);
	}

	@Override
	@Test
	public void testDeserialize() throws Exception {
		if (ArrayUtils.isEmpty(bytes)) {
			testSerialize();
		}
		
		jacksonSerializer.setType(List.class);
		list = jacksonSerializer.deserialize(bytes);
		System.out.println(list);
	}

}
