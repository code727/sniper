/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-6-17
 */

package org.sniper.beans.propertyeditors;

import java.math.BigDecimal;

/**
 * BigDecimal属性编辑器
 * @author  Daniele
 * @version 1.0
 */
public class BigDecimalPropertyEditor extends AbstractNumberPropertyEditor {
	
	/** 小数位数 */
	private int newScale;
	
	/** 第 newScale位后的小数收敛模式 */
	private int roundingMode;
	
	public BigDecimalPropertyEditor() {
		this(true);
	}
	
	public BigDecimalPropertyEditor(boolean allowEmpty) {
		this(allowEmpty, NUMBER_DEFAULT_VALUE);
	}
	
	public BigDecimalPropertyEditor(String defaultValue) {
		this(true, defaultValue);
	}
	
	public BigDecimalPropertyEditor(int newScale, int roundingMode) {
		this(true, NUMBER_DEFAULT_VALUE, newScale, roundingMode);
	}
		
	public BigDecimalPropertyEditor(boolean allowEmpty, String defaultValue) {
		this(allowEmpty, defaultValue, 2, -1);
	}
	
	public BigDecimalPropertyEditor(boolean allowEmpty, int newScale, int roundingMode) {
		this(allowEmpty, NUMBER_DEFAULT_VALUE, newScale, roundingMode);
	}
	
	public BigDecimalPropertyEditor(String defaultValue, int newScale, int roundingMode) {
		this(true, defaultValue, newScale, roundingMode);
	}
	
	public BigDecimalPropertyEditor(boolean allowEmpty, String defaultValue, int newScale, int roundingMode) {
		super(allowEmpty, defaultValue);
		this.newScale = newScale;
		this.roundingMode = roundingMode;
	}
	
	public int getNewScale() {
		return newScale;
	}

	public void setNewScale(int newScale) {
		this.newScale = newScale;
	}

	public int getRoundingMode() {
		return roundingMode;
	}

	public void setRoundingMode(int roundingMode) {
		this.roundingMode = roundingMode;
	}
	
	@Override
	protected Object handleText(String text) {
		BigDecimal decimal = new BigDecimal(text);
		return roundingMode > -1 ? decimal.setScale(newScale, roundingMode) : decimal;
	}
			
}
