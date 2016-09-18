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
 * Create Date : 2016-9-14
 */

package org.workin.nosql.mongodb.spring;

import java.util.Map;

import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.workin.nosql.mongodb.MapReduceResultModel;

/**
 * Spring MongoDB的MapReduce接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringMongoMapReduce {
	
	/**
	 * 执行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,out:{inline:1});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction);
	
	/**
	 * 在目标集合中执行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,out:{inline:1});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection,
			String mapFunction, String reduceFunction);
	
	/**
	 * 执行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"limit":limit,out:{inline:1}}
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, int limit);
	
	/**
	 * 在目标集合中执行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"limit":limit,out:{inline:1}}
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection,
			String mapFunction, String reduceFunction, int limit);
	
	/**
	 * 根据属性查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,
	 * {"query":{"propertyKey":propertyValue},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue);
	
	/**
	 * 在目标集合中根据属性查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,
	 * {"query":{"propertyKey":propertyValue},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction,
			String reduceFunction, String queryPropertyName, Object queryPropertyValue);
	
	/**
	 * 根据属性查询出文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,
	 * {"query":{"propertyKey":propertyValue},"limit":limit,out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue, int limit);
	
	/**
	 * 在目标集合中根据属性查询出最大行数的文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,
	 * {"query":{"propertyKey":propertyValue},"limit":maxRows,out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction,
			String reduceFunction, String queryPropertyName, Object queryPropertyValue, int limit);
	
	/**
	 * 根据属性组查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties);
	
	/**
	 * 在目标集合中根据属性组查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties);
	
	/**
	 * 根据属性组查询出文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties, int limit);
			
	/**
	 * 在目标集合中根据属性组查询出文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},"limit":limit,out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties, int limit);
	
	/**
	 * 查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{query},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param query
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, Query query);
	
	/**
	 * 在目标集合中查询出文档后对其进行mapReduce，并将临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{query},out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param query
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection,
			String mapFunction, String reduceFunction, Query query);
	
	/**
	 * 查询出文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{query},"limit":limit,out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param query
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, Query query, int limit);
	
	/**
	 * 在目标集合中查询出文档后对其进行mapReduce，并将返回的前limit个临时结果集保存在内存中<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{query},"limit":limit,out:{inline:1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param query
	 * @param limit
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection,
			String mapFunction, String reduceFunction, Query query, int limit);
	
	/**
	 * 执行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction,
			String reduceFunction, MapReduceOptions mapReduceOptions);
			
	/**
	 * 在目标集合中执行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction,
			String reduceFunction, MapReduceOptions mapReduceOptions);
	
	/**
	 * 根据属性查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"propertyKey":propertyValue},mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue, MapReduceOptions mapReduceOptions);
	
	/**
	 * 在目标集合中根据属性查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"propertyKey":propertyValue},mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryPropertyName
	 * @param queryPropertyValue
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue, MapReduceOptions mapReduceOptions);
	
	/**
	 * 根据属性组查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction, 
			Map<String, ?> queryProperties, MapReduceOptions mapReduceOptions);
	
	/**
	 * 在目标集合中根据属性组查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param queryProperties
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction, String reduceFunction, 
			Map<String, ?> queryProperties, MapReduceOptions mapReduceOptions);
	
    /**
     * 查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
     * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":query,mapReduceOptions});
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @param mapFunction
     * @param reduceFunction
     * @param query
     * @param mapReduceOptions
     * @return
     */
	public MapReduceResults<MapReduceResultModel> mapReduce(String mapFunction, String reduceFunction, 
			Query query, MapReduceOptions mapReduceOptions);
	
	/**
	 * 在目标集合中查询出文档后对其进行mapReduce，并由mapReduceOptions参数指定的方式返回结果<P>
	 * 实际执行的语句为:db.collection.mapReduce(mapFunction,reduceFunction,{"query":query,mapReduceOptions});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param mapFunction
	 * @param reduceFunction
	 * @param query
	 * @param mapReduceOptions
	 * @return
	 */
	public MapReduceResults<MapReduceResultModel> mapReduce(String collection, String mapFunction, 
			String reduceFunction, Query query, MapReduceOptions mapReduceOptions);

}
