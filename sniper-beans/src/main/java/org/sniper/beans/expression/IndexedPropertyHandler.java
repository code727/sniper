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
 * Create Date : 2018-12-20
 */

package org.sniper.beans.expression;

import java.lang.reflect.Array;
import java.util.List;

import org.sniper.commons.exception.NestedNullPointerException;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 已被索引的属性处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class IndexedPropertyHandler implements PropertyHandler<Object> {
	
	protected final BeanPropertyHandler beanPropertyHandler;
	
	public IndexedPropertyHandler() {
		this(null);
	}
	
	public IndexedPropertyHandler(BeanPropertyHandler beanPropertyHandler) {
		this.beanPropertyHandler = (beanPropertyHandler != null ? beanPropertyHandler : new BeanPropertyHandler());
	}

	@Override
	public boolean support(Object obj, String propertyName) {
		int startIndex = propertyName.indexOf(INDEXED_START);
		int endIndex = propertyName.indexOf(INDEXED_END);
		return startIndex > -1 && endIndex > -1 && startIndex < endIndex;
	}

	@Override
	public <V> V getPropertyValue(Object obj, String propertyName) throws Exception {
		String indexedPropertyName = StringUtils.beforeFrist(propertyName, INDEXED_START);
		Object indexedObject = beanPropertyHandler.getPropertyValue(obj, indexedPropertyName);
		return getIndexedValue(indexedObject, propertyName);
	}

	@Override
	public <V> V getConstructedPropertyValue(Object obj, String propertyName) throws Exception {
		String indexedPropertyName = StringUtils.beforeFrist(propertyName, INDEXED_START);
		Object indexedObject = beanPropertyHandler.getConstructedPropertyValue(obj, indexedPropertyName);
		return getIndexedValue(indexedObject, propertyName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPropertyValue(Object obj, String propertyName, Class<?> propertyType, Object propertyValue) throws Exception {
		String indexedPropertyName = StringUtils.beforeFrist(propertyName, INDEXED_START);
		Object indexedObject = beanPropertyHandler.getConstructedPropertyValue(obj, indexedPropertyName);
		
		if (indexedObject == null) {
			/* 如果已映射的对象为空， 但传入的属性类型为一个数组时，则构建并设置数组的值，然后将此数组设置给已映射的属性值*/
			if (ClassUtils.isArray(propertyType)) {
				Object array = Array.newInstance(propertyType.getComponentType(), 1);
				Array.set(array, 0, propertyValue);
				beanPropertyHandler.setPropertyValue(obj, indexedPropertyName, propertyType, array);
				return;
			}
			
			throw new NestedNullPointerException();
		}
		
		int index = Integer.parseInt(StringUtils.leftSubstring(propertyName, INDEXED_START, INDEXED_END));
		
		/* 如果已映射的对象为一个数组， 则将指定索引位的值合并到此数组中 */
		if (ClassUtils.isArray(indexedObject)) {
			Object[] newArray = ArrayUtils.merge((Object[]) indexedObject, index, propertyValue);
			/* 如果新数组与已映射的数组不为同一个对象，说明合并而来的数组是在原数组的基础之上库容而得到的，
			 * 由于数组并不具备引用传递的特性，因此在这种情况下，需要将新数组设置给已映射的属性值   */
			if (newArray != indexedObject)
				beanPropertyHandler.setPropertyValue(obj, indexedPropertyName, propertyType, newArray);
			
			return;
		}
		
		if (indexedObject instanceof List) {
			CollectionUtils.merge((List<Object>) indexedObject, index, propertyValue);
			return;
		}
			
	}
	
	/**
	 * 从被索引的对象中获取到指定属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param indexedObject
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> V getIndexedValue(Object indexedObject, String propertyName) {
		if (indexedObject == null)
			return null;
		
		int index = Integer.parseInt(StringUtils.leftSubstring(propertyName, INDEXED_START, INDEXED_END));
		if (indexedObject instanceof List) 
			return ((List<V>) indexedObject).get(index);
		
		if (ClassUtils.isArray(indexedObject))
			return (V) Array.get(indexedObject, index);
		
		return null;
	}

}
