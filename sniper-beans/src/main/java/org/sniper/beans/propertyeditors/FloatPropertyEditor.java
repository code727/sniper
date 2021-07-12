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
 * Create Date : 2015-6-16
 */

package org.sniper.beans.propertyeditors;

/**
 * 单精度浮点数属性编辑器
 * @author  Daniele
 * @version 1.0
 */
public class FloatPropertyEditor extends AbstractNumberPropertyEditor {
	
	public FloatPropertyEditor() {
		super();
	}
	
	public FloatPropertyEditor(boolean allowEmpty) {
		super(allowEmpty);
	}
	
	public FloatPropertyEditor(String defaultValue) {
		super(defaultValue);
	}
			
	public FloatPropertyEditor(boolean allowEmpty, String defaultValue) {
		super(allowEmpty, defaultValue);
	}
		
	@Override
	protected Object handleText(String text) {
		return Float.parseFloat(text);
	}
	
}
