/*
 * Copyright (c) 2015 org.sniper-support 
 * Create Date : 2015-1-20
 */

package org.sniper.support.test;

import org.junit.Test;
import org.sniper.commons.enums.Enums;
import org.sniper.support.operation.logic.LogicOperation;
import org.sniper.support.operation.logic.LogicOperationEnum;
import org.sniper.test.junit.BaseTestCase;

/**
 * 逻辑运算单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-20
 */
public class LogicOperationTest extends BaseTestCase {
		
	@Test
	public void testLogicOperation() {
		Enums<String, LogicOperation<Object, Object>> logicOperationEnum = LogicOperationEnum.resolve("eq");
		assertNotNull(logicOperationEnum);
		
		LogicOperation<Object, Object> operation = logicOperationEnum.getValue();
		/* 执行相等判断操作 */
		assertTrue(operation.execute(1, 1));
		assertFalse(operation.execute(1, 2));
	}

}
