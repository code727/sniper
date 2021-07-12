/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-6-21
 */

package org.sniper.image.layout;

import java.io.Serializable;

/**
 * 基本布局对象
 * @author  Daniele
 * @version 1.0
 */
public class BaseLayout implements Serializable {

	private static final long serialVersionUID = 1616689664080637930L;
	
    /** 外边距 */
    private int margin;
     
	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

}
