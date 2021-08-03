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
 * Create Date : 2021-7-12
 */

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.base.Responsible;
import org.sniper.commons.enums.MatchableEnum;
import org.sniper.commons.enums.http.HttpStatusEnum;
import org.sniper.commons.util.MapUtils;


/**
 * 响应状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ResponseStatusEnum implements Responsible<Integer>, MatchableEnum<Integer> {
	
	/** 未知错误(-1)，作为系统兜底的错误码 */
	UNKNOWN_ERROR(-1),
	
	/** 
	 * 成功(0)</P>
	 * 可用作{@link org.sniper.commons.response.Response}表示"响应成功"时的默认状态
	 */
	SUCCESS(0),
	
	/* 
	 * 注意：
	 * 1.状态码在[1,99]区间内的"成功"枚举项预留给后续版本扩展；
	 * 2.状态码在[100,399]区间内的"成功"枚举项直接使用{@link HttpStatusEnum}
	 *   的等值枚举项，不再在{@link ResponseStatusEnum}中定义。 
	 */
	/** 断路器返回 */
	CIRCUIT_BREAKER_FALLBACK(1),
	 
	/** 
	 * 错误(400)，状态码等价于{@link HttpStatusEnum#BAD_REQUEST}</P> 
	 * 可用作{@link org.sniper.commons.response.Response}表示"响应错误"时的默认状态，其含义包括业务错误和系统错误等 
	 */
	ERROR(HttpStatusEnum.BAD_REQUEST.getCode()),
	
	/* 
	 * 注意：
	 * 1.状态码在[401,599]区间内的"失败"枚举项直接使用{@link org.sniper.commons.enums.http.HttpStatusEnum}
	 *   的等值枚举项，不再在{@link org.sniper.commons.enums.status.ResponseStatusEnum}中定义；
	 * 2.状态码在[600,999]区间内的"错误"枚举项预留给后续版本扩展。
	 */
	
	// 系统相关的错误(1xxx)
	
	/** 系统错误(1000) */
	SYSTEM_ERROR(1000),
	/** 系统繁忙(1001) */
	SYSTEM_BUSY(1001),
	
	// 网络相关的错误(2xxx)
	
	/** 网络异常(2000) */
	NETWORK_ERROR(2000),
	/** 网络不可用(2001) */
	NETWORK_UNAVAILABLE(2001),
	/** 网络超时(2002) */
	NETWORK_TIMEOUT(2002),
	
	// 程序异常相关的错误(3xxx)
	
	/** 调用异常(3000) */
	CALL_EXCEPTION(3000),
	/** 本地调用异常(3001) */
	LOCAL_CALL_EXCEPTION(3001),
	/** 远程调用异常(3002) */
	REMOTE_CALL_EXCEPTION(3002),
	/** 远程调用超时(3003) */
	REMOTE_CALL_TIMEOUT(3003),
	/** 断路器异常返回(3004) */
	CIRCUIT_BREAKER_EXCEPTION(3004),
	/** 业务异常(3005) */
	BIZ_EXCEPTION(3005),
	
	// 请求相关的错误(4xxx)
	
	/** 请求错误 */
	REQUEST_ERROR(4000),
	/** 请求的正文长度超过限制(4001) */
	REQUEST_BODY_LENGTH_OVER_LIMIT(4001),
	/** IP限制不能请求该资源(4002) */
	IP_REQUEST_LIMIT(4002),
	/** 请求频次超过上限(4003) */
	REQUEST_OUT_OF_RATE_LIMIT(4003),
	/** IP请求频次超过上限(4004) */
	IP_REQUEST_OUT_OF_RATE_LIMIT(4004),
	/** 用户请求频次超过上限(4005) */
	USER_REQUEST_OUT_OF_RATE_LIMIT(4005),
	
	// 参数相关的错误(5xxx)

	/** 参数错误(5000) */
	PARAM_ERROR(5000),
	/** 参数缺失(5001) */
	PARAM_MISSING(5001),
	/** 参数校验错误(5002) */
	PARAM_VALIDATE_ERROR(5002),
	/** 参数不合法(5003) */
	PARAM_ILLEGAL(5003),
	/** 参数类型不匹配(5004) */
	PARAM_TYPE_MISMATCH(5004),
	/** 参数格式错误(5005) */
	PARAM_PATTERN_ERROR(5005),
	
	// 参数长度相关的错误(51xx)
	
	/** 参数需保持为空值(5100) */
	PARAM_KEEP_EMPTY(5100),
	/** 参数不能为空(5101) */
	PARAM_NOT_EMPTY(5101),
	/** 参数长度超出最大限制(5102) */
	PARAM_LENGTH_OUT_OF_MAX_LIMIT(5102),
	/** 参数长度未满足最小限制(5103) */
	PARAM_LENGTH_NOT_REACHED_MIN_LIMIT(5103),
	/** 参数长度必须在范围限制内(5104) */
	PARAM_LENGTH_MUST_BE_WITHIN_RANGE_LIMIT(5104),
	
	// 参数大小相关的错误(52xx)
	
	/** 参数必须等于预期(5200) */
	PARAM_MUST_EQUALS_EXPECTED(5200),
	/** 参数必须不等于预期(5201) */
	PARAM_MUST_NOT_EQUALS_EXPECTED(5201),
	/** 参数必须大于预期(5202) */
	PARAM_MUST_GREATER_THAN_EXPECTED(5202),
	/** 参数必须大于等于预期(5203) */
	PARAM_MUST_GREATER_THAN_EQUALS_EXPECTED(5203),
	/** 参数必须小于预期(5204) */
	PARAM_MUST_LESS_THAN_EXPECTED(5204),
	/** 参数必须小于等于预期(5205) */
	PARAM_MUST_LESS_THAN_EQUALS_EXPECTED(5205),
	/** 参数必须在范围限制内(5206) */
	PARAM_MUST_BE_WITHIN_RANGE_LIMIT(5206),
	/** 参数必须在范围限制外(5207) */
	PARAM_MUST_BE_OUTSIDE_RANGE_LIMIT(5207),
	
	// 参数格式相关的错误(53xx)
	
	/** 邮件参数格式错误(5300) */
	PARAM_EMAIL_PATTERN_ERROR(5300),
	/** 移动电话号码格式错误(5301) */
	PARAM_MOBILE_PATTERN_ERROR(5301),
	/** 电话号码格式错误(5302) */
	PARAM_TELEPHONE_PATTERN_ERROR(5302),
	/** IP参数格式错误(5303) */
	PARAM_IP_PATTERN_ERROR(5303),
	/** IPV4参数格式错误(5304) */
	PARAM_IPV4_PATTERN_ERROR(5304),
	/** IPV6参数格式错误(5305) */
	PARAM_IPV6_PATTERN_ERROR(5305),
	/** URL参数格式错误(5306) */
	PARAM_URL_PATTERN_ERROR(5306),
	
	// 文件相关的错误(54xx)
	
	/** 文件上传失败(5400) */
	FILE_UPLOAD_FAIL(5400),
	/** 文件下载失败(5401) */
	FILE_DOWNLOAD_FAIL(5401),
	/** 文件容量超出最大限制(5402) */
	FILE_CAPACITY_OUT_OF_MAX_LIMIT(5402),
	/** 不支持的文件类型(5403) */
	UNSUPPORTED_FILE_TYPE(5403),
	/** 不支持上传文件夹(5404) */
	UNSUPPORTED_UPLOAD_FOLDER(5404),
	/** 不支持上传空文件(5405) */
	UNSUPPORTED_UPLOAD_EMPTY_FILE(5405),
	
	// 数据相关的错误(6xxx)
	
	/** 新增失败(6000) */
	DATA_DAA_ERROR(6000),
	/** 更新失败(6001) */
	DATA_UPDATE_ERROR(6001),
	/** 删除失败(6002) */
	DATA_DELETE_ERROR(6002),
	/** 查询失败(6003) */
	DATA_QUERY_ERROR(6003),
	/** 同步失败(6004) */
	DATA_SYNC_ERROR(6004),
	
	// 数据查询相关的错误(61xx)
	
	/** 未查询到数据(6100) */
	NO_DATA_FOUND(6100),
	/** 未查询到重复数据(6101) */
	DUPLICATE_DATA_FOUND(6101),
	/** 查询到多条数据(6102) */
	MULTI_DATA_FOUND(6102),
	/** 数据已占用(6103) */
	DATA_OCCUPIED(6103),
	/** 数据已禁用(6104) */
	DATA_DISABLED(6104),
	/** 数据已删除(6105) */
	DATA_DELETED(6105),
	/** 启用时禁止更新(6106) */
	CANT_UPDATE_WHEN_ENABLED(6106),
	/** 启用时禁止删除(6107) */
	CANT_DELETE_WHEN_ENABLED(6107),
	
	// 数据异常相关的错误(62xx)
	
	/** 数据不一致(6200) */
	DATA_INCONSISTENT(6200),
	/** 数据已损坏(6201) */
	DATA_DAMAGED(6201),
	/** 数据已丢失(6202) */
	DATA_MISSING(6202),
	
	// 安全相关的错误(7xxx)
	
	/** 证书已损坏(7000) */
	CERTIFICATE_DAMAGED(7000),
	/** 证书已损坏(7001) */
	CERTIFICATE_EXPIRED(7001),
	
	// 认证/授权相关的错误(71xx)
	
	/** 未认证(7100) */
	UNAUTHENTICATE(7100),
	/** 未授权(7101) */
	UNAUTHORIZED(7101),
	/** 第三方认证失败(7102) */
	THIRDPARTY_AUTHENTICATE_FAILED(7102),
	/** 第三方授权失败(7103) */
	THIRDPARTY_AUTHORIZED_FAILED(7103),
	/** 未通过实名认证(7104) */
	REALNAME_AUTHENTICATION_NO_PASS(7104),
	
	// 签名相关的错误(72xx)
	
	/** 签名验证未通过(7200) */
	SIGNATURE_NO_PASS(7200),
	/** 第三方签名验证未通过(7201) */
	THIRDPARTY_SIGNATURE_NO_PASS(7201),
	/** 缺失关键的签名参数(7202) */
	SIGNATURE_PARAM_MISSING(7202),
	/** 签名不一致(7203) */
	SIGNATURE_INCONSISTENT(7203),
	
	// 账号密码等相关错误(73xx)
	
	/** 账号错误 */
	ACCOUNT_ERROR(7300),
	/** 密码错误 */
	PASSWORD_ERROR(7301),
	/** 密码错误次数已达最大限制 */
	PASSWORD_ERROR_COUNT_OUT_OF_MAX_LIMIT(7302),
	/** 密码已过期(7303) */
	PASSWORD_EXPIRED(7303),
	/** 密码复杂度过低(7304) */
	PASSWORD_COMPLEXITY_TOO_LOW(7304),
	/** 验证码错误(7305) */
	VERIFICATION_CODE_ERROR(7305),
	/** 验证码已过期(7306) */
	VERIFICATION_CODE_EXPIRED(7306),
	
	// 会话相关的错误(74xx)
	
	/** 会话过期(7400) */
	SESSION_EXPIRED(7400),
	/** TOKEN过期(7400) */
	TOKEN_EXPIRED(7401),
	
	// 响应相关的错误(8xxx)
	
	/** 响应错误(8000) */
	RESPONSE_ERROR(8000),
	/** 响应错误(8001) */
	RESPONSE_TIMEOUT(8001)
	
	;
	
	private static final String MESSAGE_PREFIX = "response.status.";
	private static final Map<Integer, ResponseStatusEnum> CODE_MAPPINGS = MapUtils.newHashMap(values().length);
	private static final Map<String, ResponseStatusEnum> NAME_MAPPINGS = MapUtils.newHashMap(values().length);
	
	static {
		for (ResponseStatusEnum status : values()) {
			CODE_MAPPINGS.put(status.code, status);
			NAME_MAPPINGS.put(status.name(), status);
		}
	}
	
	/** 状态码 */
	private final int code;
		
	/** 消息 */
	private final String message;

	private ResponseStatusEnum(int code) {
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
	 * 判断当前枚举是否为成功的响应
	 * @author Daniele
	 * @return
	 */
	public boolean isSuccessful() {
		return isSuccessful(code);
	}
	
	/**
	 * 判断当前枚举是否为错误的响应
	 * @author Daniele
	 * @return
	 */
	public boolean isIncorrect() {
		return isIncorrect(code);
	}
	
	/**
	 * 将指定的状态码解析成枚举对象
	 * @param code
	 * @return
	 */
	public static ResponseStatusEnum resolve(int code) {
		return CODE_MAPPINGS.get(code);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static ResponseStatusEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
	/**
	 * 判断指定的状态码是否为成功的响应
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public static boolean isSuccessful(int code) {
		return (code >= SUCCESS.code && code < ERROR.code) || HttpStatusEnum.isSuccessfulResponse(code);
	}
	
	/**
	 * 判断指定的状态码是否为错误的响应
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public static boolean isIncorrect(int code) {
		return !isSuccessful(code);
	}
		
}
