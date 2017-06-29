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
 * Create Date : 2017-3-31
 */

package org.sniper.serialization.json.jackson.codehaus;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * CodehausJackson BigDecimal类型序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CodehausJacksonBigDecimalSerializer extends JsonSerializer<BigDecimal> {
	
	private int newScale;
	
	private int roundingMode;
	
	public CodehausJacksonBigDecimalSerializer() {
		this(2, RoundingMode.DOWN.ordinal());
	}
	
	public CodehausJacksonBigDecimalSerializer(int newScale, int roundingMode) {
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
	public void serialize(BigDecimal decimal, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		jgen.writeString(decimal.setScale(newScale, roundingMode).toString());  
	}

}
