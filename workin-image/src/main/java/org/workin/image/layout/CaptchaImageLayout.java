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

package org.workin.image.layout;

import java.awt.Color;

/**
 * @description 验证码图片布局
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CaptchaImageLayout extends TextBoxLayout {

	private static final long serialVersionUID = -243543766415298588L;
	
	public static final String CUSTOM_TEXT_COLOR_NAME = "custom";
	public static final Color CUSTOM_TEXT_COLOR = new Color(19, 148, 246);
	
	/** 是否需要干扰项 */
	private boolean distracter = true;
	
	public boolean hasDistracter() {
		return distracter;
	}

	public void setDistracter(boolean distracter) {
		this.distracter = distracter;
	}

}
