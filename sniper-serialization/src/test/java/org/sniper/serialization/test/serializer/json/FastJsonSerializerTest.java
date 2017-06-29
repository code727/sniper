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
 * Create Date : 2016-7-13
 */

package org.sniper.serialization.test.serializer.json;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.IOUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.serialization.json.FastJsonSerializer;
import org.sniper.serialization.test.domain.User;
import org.sniper.serialization.test.serializer.AbstractSerializerTest;

/**
 * 阿里FastJson序列化器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastJsonSerializerTest extends AbstractSerializerTest {
	
	private FastJsonSerializer fastJsonSerializer;
	
	public FastJsonSerializerTest() {
		this.fastJsonSerializer = new FastJsonSerializer();
		this.fastJsonSerializer.setDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT);
	}

	@Override
	@Test
	public void testSerialize() throws Exception {
		bytes = fastJsonSerializer.serialize(list);
		
		String path = "C:/Users/sniper/Desktop/fastJsonSerializer.txt";
		IOUtils.write(new FileOutputStream(new File(path)), bytes);
	}

	@Override
	@Test
	public void testDeserialize() throws Exception {
		if (ArrayUtils.isEmpty(bytes)) {
			testSerialize();
		}
		
		User user = new User();
		user.setAmount(new BigDecimal("99.9999"));
//		fastJsonSerializer.serialize(user)
//		fastJsonSerializer.setType(List.class);
		
		List<LinkedHashMap<?, ?>> users = fastJsonSerializer.deserialize(bytes);
		System.out.println(users);
		
		Map<String, Object> map = MapUtils.newHashMap();
		map.put("name", "dubin");
		map.put("age", 34);
		System.out.println(fastJsonSerializer.deserialize(fastJsonSerializer.serialize(map)));
	}

}
