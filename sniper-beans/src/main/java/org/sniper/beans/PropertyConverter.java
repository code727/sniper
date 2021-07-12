/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-1-9
 */

package org.sniper.beans;

import java.beans.PropertyEditor;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.sniper.beans.propertyeditors.BooleanPropertyEditor;
import org.sniper.beans.propertyeditors.BytePropertyEditor;
import org.sniper.beans.propertyeditors.DatePropertyEditor;
import org.sniper.beans.propertyeditors.DoublePropertyEditor;
import org.sniper.beans.propertyeditors.FloatPropertyEditor;
import org.sniper.beans.propertyeditors.IntegerPropertyEditor;
import org.sniper.beans.propertyeditors.LongPropertyEditor;
import org.sniper.beans.propertyeditors.ShortPropertyEditor;
import org.sniper.beans.propertyeditors.StringBufferPropertyEditor;
import org.sniper.beans.propertyeditors.StringBuilderPropertyEditor;
import org.sniper.beans.propertyeditors.StringPropertyEditor;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;

/**
 * Property转换器实现类
 * @author  Daniele
 * @version 1.0
 */
public class PropertyConverter {
	
	private Map<Class<?>, PropertyEditor> propertyEditors;
	
	public PropertyConverter() {
		this(null);
	}
	
	public PropertyConverter(Map<Class<?>, PropertyEditor> propertyEditors) {
		if (MapUtils.isNotEmpty(propertyEditors))
			this.propertyEditors = propertyEditors;
		else {
			this.propertyEditors = MapUtils.newLinkedHashMap();
			
			this.propertyEditors.put(String.class, new StringPropertyEditor(true));
			this.propertyEditors.put(StringBuffer.class, new StringBufferPropertyEditor(true));
			this.propertyEditors.put(StringBuilder.class, new StringBuilderPropertyEditor(true));
			
			this.propertyEditors.put(boolean.class, new BooleanPropertyEditor(false));
			this.propertyEditors.put(Boolean.class, new BooleanPropertyEditor(true));
			
			this.propertyEditors.put(short.class, new ShortPropertyEditor(false));
			this.propertyEditors.put(Short.class, new ShortPropertyEditor(true));
			
			this.propertyEditors.put(byte.class, new BytePropertyEditor(false));
			this.propertyEditors.put(Byte.class, new BytePropertyEditor(true));
			
			this.propertyEditors.put(int.class, new IntegerPropertyEditor(false));
			this.propertyEditors.put(Integer.class, new IntegerPropertyEditor(true));
			
			this.propertyEditors.put(long.class, new LongPropertyEditor(false));
			this.propertyEditors.put(Long.class, new LongPropertyEditor(true));
			
			this.propertyEditors.put(float.class, new FloatPropertyEditor(false));
			this.propertyEditors.put(Float.class, new FloatPropertyEditor(true));
			
			this.propertyEditors.put(double.class, new DoublePropertyEditor(false));
			this.propertyEditors.put(Double.class, new DoublePropertyEditor(true));
			
			this.propertyEditors.put(Date.class, new DatePropertyEditor(true));
		}
	}
	
	/**
	 * 根据目标类型查找对应的属性编辑器
	 * @author Daniele 
	 * @param targetType
	 * @return
	 */
	public PropertyEditor find(Class<?> targetType) {
		return this.propertyEditors.get(targetType);
	}
	
	/**
	 * 将一个值转化成目标类型的结果
	 * @author Daniele 
	 * @param value
	 * @param targetType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T converte(Object value, Class<T> targetType) {
		PropertyEditor propertyEditor = find(targetType);
		return (T) (propertyEditor != null ? converte(propertyEditor, value) : value);
	}
	
	/**
	 * 使用指定的属性编辑器将一个值转化成目标类型的结果，当属性编辑器为空时，则直接抛出空指针异常
	 * @author Daniele 
	 * @param propertyEditor
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converte(PropertyEditor propertyEditor, Object value) {
		if (value instanceof CharSequence) 
			propertyEditor.setAsText(value.toString());
		else
			propertyEditor.setValue(value);
		
		return (T) propertyEditor.getValue();
	}
	
	/**
	 * 使用指定的属性编辑器将一个值转化成目标类型的结果
	 * @author Daniele 
	 * @param propertyEditor
	 * @param value
	 * @param targetType
	 * @return
	 */
	public static <T> T converte(PropertyEditor propertyEditor, Object value, Class<T> targetType) {
		AssertUtils.assertNotNull(propertyEditor, MessageFormat.format(
				"Property editor must not be null for target type [{0}]", targetType));
		return converte(propertyEditor, value);
	}
		
}
