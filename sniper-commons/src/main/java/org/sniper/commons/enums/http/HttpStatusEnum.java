/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-19
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.base.Responsible;
import org.sniper.commons.enums.MatchableEnum;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpStatusEnum implements Responsible<Integer>, MatchableEnum<Integer> {
	
	/** 继续(100) */
	CONTINUE(100),
	/** 交换协议(101) */
	SWITCHING_PROTOCOLS(101),
	/** 处理中(102) */
	PROCESSING(102),
	/** 早期提示(103) */
	EARLY_HINTS(103),
	
	
	/** 请求成功(200) */
	OK(200),
	/** 已建立(201) */
	CREATED(201),
	/** 已接受(202) */
	ACCEPTED(202),
	/** 非授权信息(203) */
	NON_AUTHORITATIVE_INFORMATION(203),
	/** 无内容(204) */
	NO_CONTENT(204),
	/** 重设内容(205) */
	RESET_CONTENT(205),
	/** 部分内容(206) */
	PARTIAL_CONTENT(206),
	/** 多状态(207) */
	MULTI_STATUS(207),
	/** 已报告(208) */
	ALREADY_REPORTED(208),
	/** IM使用(226) */
	IM_USED(226),
	
	
	/** 多项选择(300) */
	MULTIPLE_CHOICES(300),
	/** 永久移动(301) */
	MOVED_PERMANENTLY(301),
	/** 临时移动(302) */
	FOUND(302),
	/** 查看其它地址(303) */
	SEE_OTHER(303),
	/** 未修改(304) */
	NOT_MODIFIED(304),
	/** 使用代理服务器(305) */
	USE_PROXY(305),
	/** 临时重定向(307) */
	TEMPORARY_REDIRECT(307),
	/** 永久重定向(308) */
	PERMANENT_REDIRECT(308),
	
	
	/** 错误请求(400) */
	BAD_REQUEST(400),
	/** 未授权(401) */
	UNAUTHORIZED(401),
	/** 保留值(402) */
	PAYMENT_REQUIRED(402),
	/** 拒绝请求(403) */
	FORBIDDEN(403),
	/** 未找到(404) */
	NOT_FOUND(404),
	/** 请求方法被禁止(405) */
	METHOD_NOT_ALLOWED(405),
	/** 不能完成请求(406) */
	NOT_ACCEPTABLE(406),
	/** 需要代理身份验证(407) */
	PROXY_AUTHENTICATION_REQUIRED(407),
	/** 请求超时(408) */
	REQUEST_TIMEOUT(408),
	/** 冲突(409) */
	CONFLICT(409),
	/** 请求资源不存在(410) */
	GONE(410),
	/** 所需长度(411) */
	LENGTH_REQUIRED(411),
	/** 前提条件失败(412) */
	PRECONDITION_FAILED(412),
	/** 有效负载过大(413) */
	PAYLOAD_TOO_LARGE(413),
	/** URI太长(414) */
	URI_TOO_LONG(414),
	/** 不支持的媒体类型(415) */
	UNSUPPORTED_MEDIA_TYPE(415),
	/** 请求范围无法满足(416) */
	REQUESTED_RANGE_NOT_SATISFIABLE(416),
	/** 预期失败(417) */
	EXPECTATION_FAILED(417),
	/** 服务器拒绝煮咖啡，由反爬虫程序返回(418) */
	I_AM_A_TEAPOT(418),
	/** 错误请求(421) */
	MISDIRECTED_REQUEST(421),
	/** 不可处理的实体(422) */
	UNPROCESSABLE_ENTITY(422),
	/** 已锁定(423) */
	LOCKED(423),
	/** 依赖失败(424) */
	FAILED_DEPENDENCY(424),
	/** 太早了(425) */
	TOO_EARLY(425),
	/** 需要升级(426) */
	UPGRADE_REQUIRED(426),
	/** 前提条件(428) */
	PRECONDITION_REQUIRED(428),
	/** 请求过多(429) */
	TOO_MANY_REQUESTS(429),
	/** 请求标头字段太大(431) */
	REQUEST_HEADER_FIELDS_TOO_LARGE(431),
	/** 由于法律原因而无法使用(451) */
	UNAVAILABLE_FOR_LEGAL_REASONS(451),
	
	
	/** 内部服务器错误(500) */
	INTERNAL_SERVER_ERROR(500),
	/** 未实现(501) */
	NOT_IMPLEMENTED(501),
	/** 错误的网关(502) */
	BAD_GATEWAY(502),
	/** 暂停服务(503) */
	SERVICE_UNAVAILABLE(503),
	/** 网关超时(504) */
	GATEWAY_TIMEOUT(504),
	/** 不支持HTTP版本(505) */
	HTTP_VERSION_NOT_SUPPORTED(505),
	/** 变体也可协商(506) */
	VARIANT_ALSO_NEGOTIATES(506),
	/** 存储空间不足(507) */
	INSUFFICIENT_STORAGE(507),
	/** 检测到循环(508) */
	LOOP_DETECTED(508),
	/** 超过带宽限制(509) */
	BANDWIDTH_LIMIT_EXCEEDED(509),
	/** 不扩展(510) */
	NOT_EXTENDED(510),
	/** 需要网络验证(511) */
	NETWORK_AUTHENTICATION_REQUIRED(511);
	
	
	private static final String MESSAGE_PREFIX = "http.status.";	
	private static final Map<Integer, HttpStatusEnum> CODE_MAPPINGS = MapUtils.newHashMap(63);
	private static final Map<String, HttpStatusEnum> NAME_MAPPINGS = MapUtils.newHashMap(63);
	
	static {
		for (HttpStatusEnum status : values()) {
			CODE_MAPPINGS.put(status.code, status);
			NAME_MAPPINGS.put(status.name(), status);
		}
	}
	
	/** 状态码 */
	private final int code;
	
	/** 消息 */
	private final String message;
	
	private HttpStatusEnum(int code) {
		this.code = code;
		this.message = MESSAGE_PREFIX + code;
	}
	
	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的状态码是否匹配当前枚举对象
	 * @author Daniele
	 * @param code
	 * @return
	 */
	@Override
	public boolean match(Integer code) {
		return code != null && this.code == code.intValue();
	}
	
	@Override
	public boolean match(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 判断当前枚举是否为1xx状态消息
	 * @author Daniele
	 * @return
	 */
	public boolean is1xxInformational() {
		return HttpStatusSeriesEnum.INFORMATIONAL.equals(toSeries());
	}
	
	/**
	 * 判断当前枚举是否为2xx成功状态
	 * @author Daniele
	 * @return
	 */
	public boolean is2xxSuccessful() {
		return HttpStatusSeriesEnum.SUCCESSFUL.equals(toSeries());
	}
	
	/**
	 * 判断当前枚举是否为3xx重定向状态
	 * @author Daniele
	 * @return
	 */
	public boolean is3xxRedirection() {
		return HttpStatusSeriesEnum.REDIRECTION.equals(toSeries());
	}
	
	/**
	 * 判断当前枚举是否为4xx客户端错误状态
	 * @author Daniele
	 * @return
	 */
	public boolean is4xxClientError() {
		return HttpStatusSeriesEnum.CLIENT_ERROR.equals(toSeries());
	}
	
	/**
	 * 判断当前枚举是否为5xx服务端错误状态
	 * @author Daniele
	 * @return
	 */
	public boolean is5xxServerError() {
		return HttpStatusSeriesEnum.SERVER_ERROR.equals(toSeries());
	}
	
	/**
	 * 判断当前枚举是否为成功的HTTP响应
	 * @author Daniele
	 * @return
	 */
	public boolean isSuccessfulResponse() {
		return isSuccessfulResponse(code);
	}
	
	/**
	 * 判断当前枚举是否为错误的HTTP响应
	 * @author Daniele
	 * @return
	 */
	public boolean isIncorrectResponse() {
		return isIncorrectResponse(code);
	}
	
	/**
	 * 将当前枚举换成HTTP状态系列枚举
	 * @author Daniele
	 * @return
	 */
	public HttpStatusSeriesEnum toSeries() {
		return toSeries(code);
	}
	
	/**
	 * 将指定的状态码解析成枚举对象
	 * @param code
	 * @return
	 */
	public static HttpStatusEnum resolve(int code) {
		return CODE_MAPPINGS.get(code);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static HttpStatusEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
	/**
	 * 将指定的状态码转换成HTTP状态系列枚举
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public static HttpStatusSeriesEnum toSeries(int code) {
		return HttpStatusSeriesEnum.resolve(code / 100);
	}
	
	/**
	 * 判断指定的状态码是否为成功的HTTP响应
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public static boolean isSuccessfulResponse(int code) {
		HttpStatusSeriesEnum series = toSeries(code);
		if (series != null) {
			int key = series.getKey();
			// [100,399]区间内的状态码表示HTTP响应成功
			return key >= HttpStatusSeriesEnum.INFORMATIONAL.getKey()
					&& key <= HttpStatusSeriesEnum.REDIRECTION.getKey();
		}
		return false;
	}
	
	/**
	 * 判断指定的状态码是否为错误的HTTP响应
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public static boolean isIncorrectResponse(int code) {
		// (-∞,99]和[400,+∞)区间内的状态码表示HTTP响应错误
		return !isSuccessfulResponse(code);
	}
			
}
