/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-5
 */

package org.sniper.commons.test;

import org.junit.Assert;
import org.junit.Test;
import org.sniper.commons.enums.status.ResponseStatusEnum;
import org.sniper.commons.response.Response;
import org.sniper.commons.response.Responses;
import org.sniper.test.junit.BaseTestCase;

/**
 * 响应对象单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class ResponseTest extends BaseTestCase {
	
//	@Test
	public void testSuccessResponse() {
		Response<Object> response1 = Responses.success(); 
		Response<Object> response2 = Responses.success("test_success_response");
		Response<Object> response3 = Responses.success("Response success", "test");
		
		Assert.assertTrue(response1.successed());
		Assert.assertTrue(response2.successed());
		Assert.assertTrue(response3.successed());
		
		System.out.println(response1);
		System.out.println(response2);
		System.out.println(response3);
	}
	
//	@Test
	public void testErrorResponse() {
		Response<Object> response1 = Responses.error();
		Response<Object> response2 = Responses.error("test_error_response");
		Response<Object> response3 = Responses.error(-1, "Response failed");
		
		Assert.assertTrue(response1.errored());
		Assert.assertTrue(response2.errored());
		Assert.assertTrue(response3.errored());
		
		System.out.println(response1);
		System.out.println(response2);
		System.out.println(response3);
	}
	
	@Test
	public void buildResponse() {
		Response<Object> response1 = Responses.buildResponse(ResponseStatusEnum.SUCCESS);
		Response<Object> response2 = Responses.buildResponse(ResponseStatusEnum.ERROR);
		Response<Object> response3 = Responses.buildResponse(ResponseStatusEnum.UNKNOWN_ERROR);
		
		Assert.assertTrue(response1.successed());
		Assert.assertTrue(response2.errored());
		Assert.assertTrue(response3.errored());
		
		System.out.println(response1);
		System.out.println(response2);
		System.out.println(response3);
	}
	
}
