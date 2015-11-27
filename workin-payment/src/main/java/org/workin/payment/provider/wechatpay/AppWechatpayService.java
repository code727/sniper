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
 * Create Date : 2015-11-20
 */

package org.workin.payment.provider.wechatpay;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.payment.domain.Order;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.provider.wechatpay.enums.ReturnCode;
import org.workin.payment.provider.wechatpay.parser.DefaultWechatpayParser;
import org.workin.payment.provider.wechatpay.parser.WechatpayParser;
import org.workin.payment.service.third.AbstractThirdPaymentService;

/**
 * @description APP版微信支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class AppWechatpayService extends AbstractThirdPaymentService<Map<String, Object>> {
	
	private WechatpayParser parser;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.signature == null)
			this.signature = new WechatpaySignature();
		
		if (this.parser == null)
			this.parser = new DefaultWechatpayParser();
	}
	
	@Override
	protected ResultModel<Map<String, Object>> createPaymentParameters(Order order, Map<String,String> parameters) throws Exception {
		ResultModel<Map<String, Object>> resultModel = new ResultModel<Map<String,Object>>();
		// 第一步：统一下单
		ResultModel<String> step1Result = placeOrder(order, parameters);
		if (SystemStatus.SUCCESS.getKey().equals(step1Result.getCode())) {
			// 第二步：下单成功后，解析出返回结果
			ResultModel<Map<String, Object>> step2Result = parser.parsePlaceOrderResult(step1Result.getData());
			String code = step2Result.getCode();
			/* 解析成功后，则返回支付时的必要参数项 */
			if (SystemStatus.SUCCESS.getKey().equals(code)) {
				Map<String, Object> step2Data = step2Result.getData();
				Map<String, Object> paymentParameters = MapUtils.newHashMap();
				// 公众账号ID
				paymentParameters.put("appid", paymentContextParameters.getValue("wechatpay.appid"));
				// 商户号
				paymentParameters.put("mch_id", paymentContextParameters.getValue("wechatpay.mchid"));
				// 预支付交易会话ID
				paymentParameters.put("prepayid", step2Data.get(WechatpayParser.PREPAY_ID));
				// 扩展字段,暂填写固定值Sign=WXPay
				paymentParameters.put("package", "Sign=WXPay");
				// 随机字符串 	
//				paymentParameters.put("noncestr", step2Data.get(WechatpayParser.NONCE_STR));
				paymentParameters.put("noncestr", StringUtils.unsignedUUID(true));
				// 时间戳,东八区,自1970年1月1日 0点0分0秒以来的秒数,10位
				paymentParameters.put("timestamp", System.currentTimeMillis() / 1000);
				// 签名
//				paymentParameters.put("sign", step2Data.get(WechatpayParser.SIGN));
				String sign = signature.excute(paymentParameters, paymentContextParameters.getValue("wechatpay.seller.key", String.class));
				paymentParameters.put("sign", sign);
				resultModel.setDate(paymentParameters);
			} else if (SystemStatus.FAILED.getKey().equals(code)) {
				/* 解析失败，则直接返回第二步的状态码和消息 */
				resultModel.setCode(code);
				resultModel.setMessage(step2Result.getMessage());
			} else {
				CodeMessageModel model = updatePaymentWhenParseError(code, order.getOrderId());
				resultModel.setCode(model.getCode());
				resultModel.setMessage(model.getMessage());
			}
			
		}
		return resultModel;
	}

	@Override
	public CodeMessageModel handlePaymentResponse(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		String returnCode = paymentResponse.get("return_code");
		
		if (ReturnCode.SUCCESS.getCode().equalsIgnoreCase(returnCode)) 
			// 交易成功时更新支付记录
			updatePayment(paymentResponse);
		else {
			/* 交易未成功时，则直接返回处理结果，不对支付记录做任何更新操作 */
			result.setCode(SystemStatus.FAILED.getKey());
			result.setMessage(paymentResponse.get("return_msg"));
		}		
		return result;
	}
	
	/**
	 * @description 统一下单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @param parameters
	 * @return
	 */
	protected ResultModel<String> placeOrder(Order order, Map<String,String> parameters) {
		ResultModel<String> resultModel = new ResultModel<String>();
		
		// 统一下单请求参数项
		Map<String, Object> requestParameters = MapUtils.newHashMap();
		// 企业公众账号ID
		requestParameters.put("appid", paymentContextParameters.getValue("wechatpay.appid"));
		// 商户号
		requestParameters.put("mch_id", paymentContextParameters.getValue("wechatpay.mchid"));
		// 随机字符串，采用32位无符号全大写UUID
		requestParameters.put("nonce_str", StringUtils.unsignedUUID(true));
		// 商品名称
		requestParameters.put("body", order.getProductName());
		// 商品描述
		String description = order.getDescription();
		if (StringUtils.isNotBlank(description))
			requestParameters.put("detail", order.getDescription());
		
		// 商户交易订单号
		requestParameters.put("out_trade_no", order.getOrderId());
		
		BigDecimal amount = order.getAmount();
		if (amount == null || NumberUtils.lessThanEquals(amount, 0)) {
			amount = new BigDecimal(order.getPrice().doubleValue() * order.getQuantity() * order.getDiscount());
			requestParameters.put("total_fee", amount);
		}
		// 总金额(单位分)
		requestParameters.put("total_fee", (long) (amount.doubleValue() * 100));
		
		// 终端IP
		requestParameters.put("spbill_create_ip", parameters.get("ip"));
		// 通知回调地址
		requestParameters.put("notify_url", paymentContextParameters.getValue("wechatpay.notify.url"));
		
		String tradeType = paymentContextParameters.getValue("wechatpay.trade.type", String.class);
		// 交易类型
		requestParameters.put("trade_type", tradeType);
		if ("JSAPI".equalsIgnoreCase(tradeType))
			requestParameters.put("openid", parameters.get("openid"));
		
		/* 签名 */
		String sign = signature.excute(requestParameters, 
				paymentContextParameters.getValue("wechatpay.sller.key", String.class));
		requestParameters.put("sign", sign);
		
		try {
			// 调用微信支付统一下单请求
			String xmlString = paymentHttpTemplet.request("wechatpayPlaceOrder", parameters);
			resultModel.setDate(xmlString);
		} catch (Exception e) {
			resultModel.setCode(SystemStatus.FAILED.getKey());
			resultModel.setMessage("msg.error.call.wechatpay.placeorder.exception");
			e.printStackTrace();
		}
		
		return resultModel;
	}
	
	/**
	 * @description 当解析"统一下单"结果出现业务错误时更新支付记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @param orderId
	 * @throws Exception
	 */
	protected CodeMessageModel updatePaymentWhenParseError(String thirdCode, String orderId) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Payment payment = paymentService.findByOrderId(orderId);
		if (payment == null) 
			payment = new Payment();
		
		payment.setOrderId(orderId);
		/* 主要是更新支付记录的状态和消息 */
		payment.setStatus(ThirdPaymentStatus.getPaymentStatusCode(thirdCode));
		payment.setMessage(ThirdPaymentStatus.getPaymentMessage(thirdCode));
		if (payment.getId() != null)
			result = paymentService.update(payment);
		else
			result = paymentService.save(payment);
		
		return result;
		
	}
	
	protected CodeMessageModel updatePayment(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		String orderId = paymentResponse.get("out_trade_no");
		Payment payment = paymentService.findByOrderId(orderId);
		if (payment == null) {
			payment = new Payment();
			payment.setAmount(orderService.findByOrderId(orderId).getAmount());
		}
		payment.setOrderId(orderId);
		payment.setThirdOrderId(paymentResponse.get("transaction_id"));
		return result;
	}
	
}
