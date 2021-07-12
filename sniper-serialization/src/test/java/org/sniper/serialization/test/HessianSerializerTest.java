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
 * Create Date : 2017年12月11日
 */

package org.sniper.serialization.test;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.IOUtils;
import org.sniper.serialization.hessian.HessianSerializer;
import org.sniper.test.domain.User;

/**
 * @author  Daniele
 * @version 1.0
 */
public class HessianSerializerTest extends AbstractSerializerTest {
	
	private HessianSerializer serializer = new HessianSerializer();
	
	private byte[] bytes;
	
//	@Test
	public void testSerialize() throws Exception {
		bytes = serializer.serialize(list);
		assertTrue(ArrayUtils.isNotEmpty(bytes));
		
		System.out.println(serializer.serializeToString(list));
		// 写入目标文件查看序列化结果
		IOUtils.write(new FileOutputStream(new File(
				"C:/Users/Daniele/Desktop/HessianSerializer.txt")), bytes);
	}

	@Test
	public void testDeserialize() throws Exception {
		if (ArrayUtils.isEmpty(bytes))
			testSerialize();
		
		User[] result = serializer.deserialize(serializer.serializeToString(list), User[].class);
		User user = result[0];
		
		System.out.println(user.getName());
		System.out.println(user.getAmount());
		System.out.println(user.getCreateTime());
	}

}
