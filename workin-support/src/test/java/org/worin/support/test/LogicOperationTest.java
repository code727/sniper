/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-20
 */

package org.worin.support.test;

import org.junit.Test;
import org.workin.commons.enums.EnumObject;
import org.workin.support.LogicOperation;
import org.workin.support.LogicOperationEnum;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 逻辑运算单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-20
 */
public class LogicOperationTest extends BaseTestCase {
	
	@Test
	public void testLogicEnum() {
		assertTrue(LogicOperationEnum.exist("eq"));
		assertTrue(LogicOperationEnum.exist("=="));
		assertTrue(LogicOperationEnum.exist(">"));
		assertTrue(LogicOperationEnum.exist(">="));
		assertTrue(LogicOperationEnum.exist("<"));
		assertTrue(LogicOperationEnum.exist("<="));
		
		assertEquals(LogicOperationEnum.getAll().size(), 6);
	}
	
	@Test
	public void testLogicOperation() {
		EnumObject<String, LogicOperation<Object, Object>> enumObject = LogicOperationEnum.get("eq");
		LogicOperation<Object, Object> operation = enumObject.getValue();
		assertNotNull(operation);
		
		/* 执行相等判断操作 */
		assertTrue(operation.execute(1, 1));
		assertFalse(operation.execute(1, 2));
	}

}
