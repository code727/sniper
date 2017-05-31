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

package org.workin.beans.propertyeditors;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal属性编辑器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BigDecimalPropertyEditor extends NumberPropertyEditor {
	
	private int newScale;
	
	private int roundingMode;
	
	public BigDecimalPropertyEditor() {
		this(true);
	}
	
	public BigDecimalPropertyEditor(boolean allowEmpty) {
		this(allowEmpty, NUMBER_PROPERTY_DEFAULT_VALUE);
	}
	
	public BigDecimalPropertyEditor(String defaultValue) {
		this(true, defaultValue);
	}
	
	public BigDecimalPropertyEditor(int newScale, int roundingMode) {
		this(true, NUMBER_PROPERTY_DEFAULT_VALUE, newScale, roundingMode);
	}
		
	public BigDecimalPropertyEditor(boolean allowEmpty, String defaultValue) {
		this(allowEmpty, defaultValue, 2, RoundingMode.DOWN.ordinal());
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
		return new BigDecimal(text).setScale(newScale, roundingMode);
	}
		
}
