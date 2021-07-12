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
 * Create Date : 2015-2-2
 */

package org.sniper.orm.hibernate.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.sniper.commons.entity.IdEntity;

/**
 * UUID2主键类型实体抽象类
 * @author  Daniele
 * @version 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Uuid2Entity extends IdEntity<String> {
	
	@Id
	@GeneratedValue(generator = "uuid2Generator")
	@GenericGenerator(name = "uuid2Generator", strategy = "uuid2")
	public String getId() {
		return super.getId();
	}
	
	@Override
	public void setId(String id) {
		super.setId(id);
	}

}
