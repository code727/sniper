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
import org.sniper.commons.enums.http.HttpStatusEnum;
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
	
	private static final String TEST_DATA = "test_data";
	
	private static final String TEST_ERROR_DATA = "test_error_data";
	
	@Test
	public void testSuccessResponse() {
		Response<Object> response1 = Responses.success(); 
		Assert.assertTrue(response1.successed());
		System.out.println(response1);
		
		Response<Object> response2 = Responses.success(TEST_DATA);
		Assert.assertTrue(response2.successed());
		System.out.println(response2);
		
		Response<Object> response3 = Responses.success("响应成功", TEST_DATA);
		Assert.assertTrue(response3.successed());
		System.out.println(response3);
		
		Response<Object> response4 = Responses.success(399, "响应成功");
		Assert.assertTrue(response4.successed());
		System.out.println(response4);
		
		Response<Object> response5 = Responses.success(399, "响应成功", TEST_DATA);
		Assert.assertTrue(response5.successed());
		System.out.println(response5);
		
		Response<Object> response6 = Responses.success(ResponseStatusEnum.CIRCUIT_BREAKER_FALLBACK);
		Assert.assertTrue(response6.successed());
		System.out.println(response6);
		
		Response<Object> response7 = Responses.success(ResponseStatusEnum.CIRCUIT_BREAKER_FALLBACK, TEST_DATA);
		Assert.assertTrue(response7.successed());
		System.out.println(response7);
		
		Response<Object> response8 = Responses.success(HttpStatusEnum.OK);
		Assert.assertTrue(response8.successed());
		System.out.println(response8);
		
		Response<Object> response9 = Responses.success(HttpStatusEnum.CREATED, TEST_DATA);
		Assert.assertTrue(response9.successed());
		System.out.println(response9);
		
		Response<Object> response10 = Responses.httpSuccess();
		Assert.assertTrue(response10.successed());
		System.out.println(response10);
		
		Response<Object> response11 = Responses.httpSuccess(TEST_DATA);
		Assert.assertTrue(response11.successed());
		System.out.println(response11);
		
		try {
			Responses.success(HttpStatusEnum.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
			System.out.println(e);
		}
	}
	
	@Test
	public void testErrorResponse() {
		Response<Object> response1 = Responses.error();
		Assert.assertTrue(response1.errored());
		System.out.println(response1);
		
		Response<Object> response2 = Responses.error(TEST_ERROR_DATA);
		Assert.assertTrue(response2.errored());
		System.out.println(response2);
		
		Response<Object> response3 = Responses.error("响应错误", TEST_ERROR_DATA);
		Assert.assertTrue(response3.errored());
		System.out.println(response3);
		
		Response<Object> response4 = Responses.error(400, "响应错误");
		Assert.assertTrue(response4.errored());
		System.out.println(response4);
		
		Response<Object> response5 = Responses.error(401, "响应错误", TEST_ERROR_DATA);
		Assert.assertTrue(response5.errored());
		System.out.println(response5);
		
		Response<Object> response6 = Responses.error(ResponseStatusEnum.UNKNOWN_ERROR);
		Assert.assertTrue(response6.errored());
		System.out.println(response6);
		
		Response<Object> response7 = Responses.error(ResponseStatusEnum.UNKNOWN_ERROR, TEST_ERROR_DATA);
		Assert.assertTrue(response7.errored());
		System.out.println(response7);
		
		Response<Object> response8 = Responses.error(HttpStatusEnum.UNAUTHORIZED);
		Assert.assertTrue(response8.errored());
		System.out.println(response8);
		
		Response<Object> response9 = Responses.error(HttpStatusEnum.FORBIDDEN, TEST_ERROR_DATA);
		Assert.assertTrue(response9.errored());
		System.out.println(response9);
		
		Response<Object> response10 = Responses.httpError();
		Assert.assertTrue(response10.errored());
		System.out.println(response10);
		
		Response<Object> response11 = Responses.httpError();
		Assert.assertTrue(response11.errored());
		System.out.println(response11);
		
		try {
			Responses.error(HttpStatusEnum.OK);
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
			System.out.println(e);
		}
		
		try {
			Responses.error(ResponseStatusEnum.SUCCESS);
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
			System.out.println(e);
		}
	}
	
	@Test
	public void testBuildResponse() {
		Response<Object> response1 = Responses.build(ResponseStatusEnum.SUCCESS);
		Assert.assertTrue(response1.successed());
		System.out.println(response1);
		
		Response<Object> response2 = Responses.build(ResponseStatusEnum.ERROR, TEST_ERROR_DATA);
		Assert.assertTrue(response2.errored());
		System.out.println(response2);
		
		Response<Object> response3 = Responses.build(HttpStatusEnum.TEMPORARY_REDIRECT);
		Assert.assertTrue(response3.successed());
		System.out.println(response3);
		
		Response<Object> response4 = Responses.build(HttpStatusEnum.BAD_GATEWAY, TEST_ERROR_DATA);
		Assert.assertTrue(response4.errored());
		System.out.println(response4);
		
		Response<Object> response5 = Responses.build(Integer.MAX_VALUE, "响应错误");
		Assert.assertTrue(response5.errored());
		System.out.println(response5);
		
		Response<Object> response6 = Responses.build(Integer.MIN_VALUE, "响应错误", TEST_ERROR_DATA);
		Assert.assertTrue(response6.errored());
		System.out.println(response6);
	}
	
}
