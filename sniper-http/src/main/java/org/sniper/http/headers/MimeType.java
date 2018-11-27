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
 * Create Date : 2017-8-23
 */

package org.sniper.http.headers;

import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Locale;
import java.util.Map;

import org.sniper.beans.parameter.DefaultUnmodifiableParameters;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * MIME类型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MimeType extends DefaultUnmodifiableParameters<String, Object> {
	
	/** 主类型 */
	private final String mainType;
	
	/** 子类型 */
	private final String subType;
	
	/** 类型 */
	private final String type;
	
	private static final BitSet TOKEN;
	
	/** 字符集参数名 */
	public static final String PARAM_CHARSET = "charset";
	
	static {
		BitSet ctl = new BitSet(128);
		for (int i = 0; i <= 31; i++) {
			ctl.set(i);
		}
		ctl.set(127);

		BitSet separators = new BitSet(128);
		separators.set('(');
		separators.set(')');
		separators.set('<');
		separators.set('>');
		separators.set('@');
		separators.set(',');
		separators.set(';');
		separators.set(':');
		separators.set('\\');
		separators.set('\"');
		separators.set('/');
		separators.set('[');
		separators.set(']');
		separators.set('?');
		separators.set('=');
		separators.set('{');
		separators.set('}');
		separators.set(' ');
		separators.set('\t');

		TOKEN = new BitSet(128);
		TOKEN.set(0, 128);
		TOKEN.andNot(ctl);
		TOKEN.andNot(separators);
	}
		
	public MimeType() {
		this(null);
	}
	
	public MimeType(String mainType) {
		this(mainType, (String) null);
	}
	
	public MimeType(String mainType, Charset charset) {
		this(mainType, null, charset);
	}
		
	public MimeType(String mainType, String subType) {
		this(mainType, subType, (Charset) null);
	}
	
	public MimeType(String mainType, String subType, Charset charset) {
		this(mainType, subType, buildCharsetParameter(charset));
	}
		
	public MimeType(String mainType, Map<String, Object> parameters) {
		this(mainType, null, parameters);
	}
	
	public MimeType(MimeType mimeType, Charset charset) {
		this(mimeType, buildCharsetParameter(mimeType, charset));
	}
	
	public MimeType(MimeType mimeType, Map<String, Object> parameters) {
		this(mimeType != null ? mimeType.getType() : StringUtils.ANY,
				mimeType != null ? mimeType.getSubType() : StringUtils.ANY, parameters);
	}
				
	public MimeType(String mainType, String subType, Map<String, Object> parameters) {
		super(parameters);
		checkTypes(this.mainType = buildMainType(mainType), this.subType = buildSubtype(subType));
		this.type = this.mainType + StringUtils.FORWARD_SLASH + this.subType;
	}
				
	/**
	 * 构建字符集编码参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param charset
	 * @return
	 */
	private static Map<String, Object> buildCharsetParameter(Charset charset) {
		return buildCharsetParameter(null, charset);
	}
	
	/**
	 * 构建字符集编码参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mimeType
	 * @param charset
	 * @return
	 */
	private static Map<String, Object> buildCharsetParameter(MimeType mimeType, Charset charset) {
		if (charset == null)
			return null;
		
		Map<String, Object> parameters;
		if (mimeType != null)
			parameters = MapUtils.newLinkedHashMap(mimeType.getParameterItems());
		else
			parameters = MapUtils.newLinkedHashMap(1);
		
		parameters.put(PARAM_CHARSET, charset.name());
		
		return parameters;
	}
		
	/**
	 * 构建主类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mainType
	 * @return
	 */
	protected String buildMainType(String mainType) {
		if (StringUtils.isNotBlank(mainType)) {
			checkToken(mainType);
			return mainType.trim().toLowerCase(Locale.ENGLISH);
		}
			
		return StringUtils.ANY;
	}
	
	/**
	 * 构建子类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param subType
	 * @return
	 */
	protected String buildSubtype(String subType) {
		if (StringUtils.isNotBlank(subType)) {
			checkToken(subType);
			return subType.trim().toLowerCase(Locale.ENGLISH);
		}
		
		return StringUtils.ANY;
	}
	
	/**
	 * 重写父类构建参数方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameters
	 * @return
	 */
	@Override
	protected Map<String, Object> buildParameters(Map<String, Object> parameters) {
		if (MapUtils.isEmpty(parameters))
			return null;
		
		Map<String, Object> map = MapUtils.newLinkedHashMap();
		String attribute;
		Object value;
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			attribute = entry.getKey();
			value = entry.getValue();
			checkParameter(attribute, value);
			map.put(attribute, value);
		}
		
		return map;
	}
	
	/**
	 * 标记检查
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param token
	 */
	private void checkToken(String token) {
		for (int i = 0; i < token.length(); i++ ) {
			char ch = token.charAt(i);
			AssertUtils.assertTrue(TOKEN.get(ch), "Invalid token character '" + ch + "' in token \"" + token + "\"");
		}
	}
	
	/**
	 * 检查类型的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @param subtype
	 */
	private void checkTypes(String mainType, String subType) {
		// 不允许*/XXX的MimeType
		AssertUtils.assertFalse(StringUtils.ANY.equals(mainType) && !StringUtils.ANY.equals(subType), 
				"Subtype must not be '" + subType + "' when type is any '*'");
	}
	
	/**
	 * 检查参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param attribute
	 * @param value
	 */
	protected void checkParameter(String attribute, Object value) {
		AssertUtils.assertNotBlank(attribute, "Parameter attribute name must not be null or blank");
		
		if (value instanceof CharSequence) {
			AssertUtils.assertNotBlank(value.toString(), "Parameter attribute '" + attribute + "' string value must not be null or blank");
			if (PARAM_CHARSET.equals(attribute))
				// 检查字符集的合法性
				Charset.forName(value.toString());
		} else
			AssertUtils.assertNotNull(value, "Parameter attribute '" + attribute + "' value must not be null");
	}

	/**
	 * 获取主类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMainType() {
		return mainType;
	}

	/**
	 * 获取子类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 获取类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getType() {
		return type;
	}
		
	/**
	 * 获取MIME类型的字符集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Charset getCharset() {
		Object value = getValue(PARAM_CHARSET);
		
		if (value instanceof Charset)
			return (Charset) value;
		
		String charsetName = ObjectUtils.toString(value);
		return StringUtils.isNotBlank(charsetName) ? Charset.forName(charsetName) : null;
	}
	
	/**
	 * 是否为通配符主类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isWildcardMainType() {
		return StringUtils.ANY.equals(mainType);
	}
	
	/**
	 * 是否为通配符子类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isWildcardSubType() {
		return StringUtils.ANY.equals(subType) || subType.startsWith("*+");
	}
	
	/**
	 * 是否为通配符类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isWildcardType() {
		return isWildcardType() && isWildcardSubType();
	}
	
	/**
	 * 判断是否包含指定的MimeType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param other
	 * @return
	 */
	public boolean includes(MimeType other) {
		if (other == null) 
			return false;
		
		if (this.isWildcardMainType()) 
			return true;
		
		if (mainType.equals(other.getMainType())) {
			if (subType.equals(other.getSubType())) 
				return true;
			
			if (this.isWildcardSubType()) {
				int thisPlusIdx = subType.indexOf('+');
				if (thisPlusIdx == -1) 
					return true;
				else {
					int otherPlusIdx = other.getSubType().indexOf('+');
					if (otherPlusIdx != -1) {
						String thisSubtypeNoSuffix = subType.substring(0, thisPlusIdx);
						String thisSubtypeSuffix = subType.substring(thisPlusIdx + 1);
						String otherSubtypeSuffix = other.getSubType().substring(otherPlusIdx + 1);
						if (thisSubtypeSuffix.equals(otherSubtypeSuffix) && StringUtils.ANY.equals(thisSubtypeNoSuffix)) 
							return true;
					}
				}
			}
		}
		
		return false;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getType());
		
		String attribute;
		for (Map.Entry<String, Object> entry : getParameterItems().entrySet()) {
			// 质量价值等于默认的1.0时不予以显示
			if (QualityFactor.PARAM_QUALITY_FACTOR.equals(attribute = entry.getKey())
					&& NumberUtils.equals(entry.getValue(), QualityFactor.MAX_DEFAULT_VALUE)) {
				continue;
			}
			builder.append(StringUtils.SEMICOLON).append(attribute).append(StringUtils.ASSIGNMENT).append(entry.getValue());
		}
		
		return builder.toString();
	}
						
}
