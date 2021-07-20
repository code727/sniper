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

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * HTTP状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpStatusEnum {
	
	/** 继续 */
	CONTINUE(100),
	/** 交换协议 */
	SWITCHING_PROTOCOLS(101),
	/** 处理中 */
	PROCESSING(102),
	/** 早期提示 */
	EARLY_HINTS(103),
	
	
	/** 请求成功 */
	OK(200),
	/** 已建立 */
	CREATED(201),
	/** 已接受 */
	ACCEPTED(202),
	/** 非授权信息 */
	NON_AUTHORITATIVE_INFORMATION(203),
	/** 无内容 */
	NO_CONTENT(204),
	/** 重设内容 */
	RESET_CONTENT(205),
	/** 部分内容 */
	PARTIAL_CONTENT(206),
	/** 多状态 */
	MULTI_STATUS(207),
	/** 已报告 */
	ALREADY_REPORTED(208),
	/** IM使用 */
	IM_USED(226),
	
	
	/** 多项选择 */
	MULTIPLE_CHOICES(300),
	/** 永久移动 */
	MOVED_PERMANENTLY(301),
	/** 临时移动 */
	FOUND(302),
	/** 查看其它地址 */
	SEE_OTHER(303),
	/** 未修改 */
	NOT_MODIFIED(304),
	/** 使用代理服务器 */
	USE_PROXY(305),
	/** 临时重定向 */
	TEMPORARY_REDIRECT(307),
	/** 永久重定向 */
	PERMANENT_REDIRECT(308),
	
	
	/** 错误的请求 */
	BAD_REQUEST(400),
	/** 未经授权 */
	UNAUTHORIZED(401),
	/** 保留值 */
	PAYMENT_REQUIRED(402),
	/** 拒绝请求 */
	FORBIDDEN(403),
	/** 未找到 */
	NOT_FOUND(404),
	/** 请求的方法被禁止 */
	METHOD_NOT_ALLOWED(405),
	/** 不能完成请求 */
	NOT_ACCEPTABLE(406),
	/** 需要代理身份验证 */
	PROXY_AUTHENTICATION_REQUIRED(407),
	/** 请求超时 */
	REQUEST_TIMEOUT(408),
	/** 冲突 */
	CONFLICT(409),
	/** 请求资源不存在 */
	GONE(410),
	/** 所需长度 */
	LENGTH_REQUIRED(411),
	/** 前提条件失败 */
	PRECONDITION_FAILED(412),
	/** 有效负载过大 */
	PAYLOAD_TOO_LARGE(413),
	/** URI太长 */
	URI_TOO_LONG(414),
	/** 不支持的媒体类型 */
	UNSUPPORTED_MEDIA_TYPE(415),
	/** 范围请求无法满足 */
	REQUESTED_RANGE_NOT_SATISFIABLE(416),
	/** 预期失败 */
	EXPECTATION_FAILED(417),
	/** 服务器拒绝煮咖啡，由反爬虫程序返回 */
	I_AM_A_TEAPOT(418),
	/** 错误请求 */
	MISDIRECTED_REQUEST(421),
	/** 不可处理的实体 */
	UNPROCESSABLE_ENTITY(422),
	/** 已锁定 */
	LOCKED(423),
	/** 依赖失败 */
	FAILED_DEPENDENCY(424),
	/** 太早了 */
	TOO_EARLY(425),
	/** 需要升级 */
	UPGRADE_REQUIRED(426),
	/** 前提条件 */
	PRECONDITION_REQUIRED(428),
	/** 请求过多 */
	TOO_MANY_REQUESTS(429),
	/** 请求标头字段太大 */
	REQUEST_HEADER_FIELDS_TOO_LARGE(431),
	/** 由于法律原因而无法使用 */
	UNAVAILABLE_FOR_LEGAL_REASONS(451),
	
	
	/** 内部服务器错误 */
	INTERNAL_SERVER_ERROR(500),
	/** 未实现 */
	NOT_IMPLEMENTED(501),
	/** 错误的网关 */
	BAD_GATEWAY(502),
	/** 暂停服务 */
	SERVICE_UNAVAILABLE(503),
	/** 网关超时 */
	GATEWAY_TIMEOUT(504),
	/** 不支持HTTP版本 */
	HTTP_VERSION_NOT_SUPPORTED(505),
	/** 变体也可协商 */
	VARIANT_ALSO_NEGOTIATES(506),
	/** 存储空间不足 */
	INSUFFICIENT_STORAGE(507),
	/** 检测到循环 */
	LOOP_DETECTED(508),
	/** 超过带宽限制 */
	BANDWIDTH_LIMIT_EXCEEDED(509),
	/** 不扩展*/
	NOT_EXTENDED(510),
	/** 需要网络验证 */
	NETWORK_AUTHENTICATION_REQUIRED(511);
	
	private static final String MESSAGE_PREFIX = "http.status.";
	
	private static final Map<Integer, HttpStatusEnum> CODE_MAPPINGS = MapUtils.newHashMap(63);
	
	static {
		for (HttpStatusEnum httpStatus : values()) {
			CODE_MAPPINGS.put(httpStatus.code, httpStatus);
		}
	}
	
	/** 状态码 */
	private final int code;
	
	/** 消息 */
	private final String message;
	
	private HttpStatusEnum(int code) {
		this.code = code;
		this.message = MessageUtils.getClassMessage(getClass(), MESSAGE_PREFIX + code);
	}
	
	/**
	 * 获取状态码
	 * @author Daniele
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获取消息
	 * @author Daniele
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的状态码是否匹配当前枚举对象
	 * @param code
	 * @return
	 */
	public boolean matches(int code) {
		return this.code == code;
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
	 * 判断当前枚举是否为1xx状态消息
	 * @author Daniele
	 * @return
	 */
	public boolean is1xxInformation() {
		return this.code >= 100 && this.code <= 199;
	}
	
	/**
	 * 判断当前枚举是否为2xx成功状态
	 * @author Daniele
	 * @return
	 */
	public boolean is2xxSuccessed() {
		return this.code >= 200 && this.code <= 299;
	}
	
	/**
	 * 判断当前枚举是否为3xx重定向状态
	 * @author Daniele
	 * @return
	 */
	public boolean is3xxRedirection() {
		return this.code >= 300 && this.code <= 399;
	}
	
	/**
	 * 判断当前枚举是否为4xx客户端请求错误状态
	 * @author Daniele
	 * @return
	 */
	public boolean is4xxClientError() {
		return this.code >= 400 && this.code <= 499;
	}
	
	/**
	 * 判断当前枚举是否为5xx服务端错误状态
	 * @author Daniele
	 * @return
	 */
	public boolean is5xxServerError() {
		return this.code >= 500 && this.code <= 599;
	}
	
	/**
	 * 判断当前枚举代表的HTTP请求是否已成功
	 * @author Daniele
	 * @return
	 */
	public boolean requestSuccess() {
		// 状态码在[100,399]区间内表示请求成功
		return is1xxInformation() || is2xxSuccessed() || is3xxRedirection();
	}
	
	/**
	 * 判断当前枚举代表的HTTP请求是否发生错误
	 * @author Daniele
	 * @return
	 */
	public boolean requestError() {
		// 状态码在[400,599]区间内表示请求错误
		return is4xxClientError() || is5xxServerError();
	}
		
}
