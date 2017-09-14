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
public class MimeType extends DefaultUnmodifiableParameters<String, Object> implements Cloneable {
	
	/** 主类型 */
	private final String type;
	
	/** 子类型 */
	private final String subtype;
	
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
	
	public MimeType(String type) {
		this(type, (String) null);
	}
	
	public MimeType(String type, Charset charset) {
		this(type, null, charset);
	}
		
	public MimeType(String type, String subtype) {
		this(type, subtype, (Charset) null);
	}
	
	public MimeType(String type, String subtype, Charset charset) {
		this(type, subtype, buildCharsetParameter(charset));
	}
		
	public MimeType(String type, Map<String, Object> parameters) {
		this(type, null, parameters);
	}
	
	public MimeType(MimeType mimeType, Charset charset) {
		this(mimeType, buildCharsetParameter(mimeType, charset));
	}
	
	public MimeType(MimeType mimeType, Map<String, Object> parameters) {
		this(mimeType != null ? mimeType.getType() : StringUtils.ANY,
				mimeType != null ? mimeType.getSubtype() : StringUtils.ANY, parameters);
	}
				
	public MimeType(String type, String subtype, Map<String, Object> parameters) {
		super(parameters);
		checkTypes(this.type = buildType(type), this.subtype = buildSubtype(subtype));
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
	 * @param type
	 * @return
	 */
	protected String buildType(String type) {
		if (StringUtils.isNotBlank(type)) {
			checkToken(type);
			return type.trim().toLowerCase(Locale.ENGLISH);
		}
			
		return StringUtils.ANY;
	}
	
	/**
	 * 构建子类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param subtype
	 * @return
	 */
	protected String buildSubtype(String subtype) {
		if (StringUtils.isNotBlank(subtype)) {
			checkToken(subtype);
			return subtype.trim().toLowerCase(Locale.ENGLISH);
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
	private void checkTypes(String type, String subtype) {
		// 不允许*/XXX的MimeType
		AssertUtils.assertFalse(StringUtils.ANY.equals(type) && !StringUtils.ANY.equals(subtype), 
				"Subtype must not be '" + subtype + "' when type is any '*'");
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

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
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
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(type).append(StringUtils.FORWARD_SLASH).append(subtype);
		
		String attribute;
		for (Map.Entry<String, Object> entry : getParameterItems().entrySet()) {
			// 质量价值等于默认的1.0时不予以显示
			if (QualityFactor.PARAM_QUALITY_FACTOR.equals(attribute = entry.getKey())
					&& NumberUtils.equals(entry.getValue(), QualityFactor.MAX_DEFAULT)) {
				continue;
			}
			builder.append(StringUtils.SEMICOLON).append(attribute).append(StringUtils.ASSIGNMENT).append(entry.getValue());
		}
		
		return builder.toString();
	}
				
}
