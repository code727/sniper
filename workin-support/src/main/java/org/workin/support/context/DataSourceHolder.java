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
 * Create Date : 2015-5-12
 */

package org.workin.support.context;

/**
 * 数据源上下文工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DataSourceHolder extends ApplicationContextHolder {
	
	public static final String CURRENT_DATASOURCE_NAME = "current_datasource_name";
	
	/**
	 * 设置数据源标识到线程上下文变量中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
    public static void setDataSourceName(String name){  
       setAttribute(CURRENT_DATASOURCE_NAME, name);
    }  
      
    /**
     * 从线程上下文变量中获取数据源标识
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @return
     */
    public static String getDataSourceName() {  
        return (String) getAttribute(CURRENT_DATASOURCE_NAME);
    }  
                
}
