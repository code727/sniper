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
 * Create Date : 2015-11-25
 */

package org.sniper.payment.provider.wechatpay.parser;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.sniper.commons.enums.status.SystemStatus;
import org.sniper.commons.model.impl.ResultModel;
import org.sniper.commons.util.IOUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.payment.provider.wechatpay.enums.ResultCode;
import org.sniper.payment.provider.wechatpay.enums.ReturnCode;
import org.sniper.payment.provider.wechatpay.enums.TradeType;

/**
 * 微信支付解析器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class DefaultWechatpayParser implements WechatpayParser {
	
	@Override
	public ResultModel<Map<String, Object>> parsePlaceOrderResult(String result) {
		ResultModel<Map<String, Object>> resultModel = new ResultModel<Map<String,Object>>();
		try {
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			// 返回状态码
			String returnCode = document.createXPath(root.getPath() + "/" + RETURN_CODE).selectSingleNode(document).getText();
			if (ReturnCode.SUCCESS.getCode().equals(returnCode)) {
				// 业务结果码
				String resultCode = document.createXPath(root.getPath() + "/" + RESULT_CODE).selectSingleNode(document).getText();
				if (ResultCode.SUCCESS.getCode().equals(resultCode)) {
					/* return_code 和result_code都为SUCCESS时返回必要的支付请求参数项 */
					Map<String, Object> paymentRequestParameters = MapUtils.newHashMap();
					
					/* 微信返回的随机字符串和签名 */
					paymentRequestParameters.put(NONCE_STR, document.createXPath(root.getPath() + "/" + NONCE_STR).selectSingleNode(document).getText());
					paymentRequestParameters.put(SIGN, document.createXPath(root.getPath() + "/" + SIGN).selectSingleNode(document).getText());
					
					String tradeType = document.createXPath(root.getPath() + "/" + TRADE_TYPE).selectSingleNode(document).getText();
					paymentRequestParameters.put(TRADE_TYPE, tradeType);
					paymentRequestParameters.put(PREPAY_ID, document.createXPath(root.getPath() + "/" + PREPAY_ID).selectSingleNode(document).getText());
					if (TradeType.JSAPI.equals(tradeType))
						paymentRequestParameters.put(CODE_URL, document.createXPath(root.getPath() + "/" + CODE_URL).selectSingleNode(document).getText());
							
					resultModel.setData(paymentRequestParameters);
				} else {
					// 业务结果
					Node errCodeNode = document.createXPath(root.getPath() + "/" + ERR_CODE).selectSingleNode(document);
					if (errCodeNode != null) {
						// 业务错误
						resultModel.setCode(errCodeNode.getText());
						Node errCodeDesNode = document.createXPath(root.getPath() + "/" + ERR_CODE_DES).selectSingleNode(document);
						if (errCodeDesNode != null)
							resultModel.setMessage(errCodeDesNode.getText());
					}
					else {
						resultModel.setCode(SystemStatus.FAILED.getKey());
						resultModel.setMessage("msg.place.order.failed");
					}
				}
			} else {
				/* return_code未成功时，则返回失败状态，并且消息即为return_msg的值 */
				resultModel.setCode(SystemStatus.FAILED.getKey());
				Node returnMsgNode = document.createXPath(root.getPath() + "/" + RETURN_MSG).selectSingleNode(document);
				if (returnMsgNode != null)
					resultModel.setMessage(returnMsgNode.getText());
				else
					resultModel.setMessage("msg.place.order.failed");
			}
		}  catch (DocumentException e) {
			resultModel.setCode(SystemStatus.FAILED.getKey());
			resultModel.setMessage("msg.error.parse.wechatpay.placeorder.exception");
			e.printStackTrace();
		}
		return resultModel;
	}

	@Override
	public Map<String, String> parseNotifyRequest(HttpServletRequest request) throws Exception {
		Document document = DocumentHelper.parseText(IOUtils.read(request.getInputStream()));
		Element root = document.getRootElement();
		Map<String, String> requestParameters = MapUtils.newHashMap();
		requestParameters.putAll(parseNotifyRequiredParameters(document, root));
		requestParameters.putAll(parseNotifyUnrequiredParameters(document, root, requestParameters.get("return_code").toString()));
		return requestParameters;
	}
	
	/**
	 * 解析出通知中的必要参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param document
	 * @param root
	 * @return
	 */
	protected Map<String, String> parseNotifyRequiredParameters(Document document, Element root) {
		Map<String, String> requiredParameters = MapUtils.newHashMap();
		String returnCode = document.createXPath(root.getPath() + "/" + RETURN_CODE).selectSingleNode(document).getText();
		requiredParameters.put(RETURN_CODE, returnCode);
		if (ReturnCode.SUCCESS.getCode().equals(returnCode)) {
			// 公众账号ID
			requiredParameters.put(APPID, document.createXPath(root.getPath() + "/" + APPID).selectSingleNode(document).getText());
			// 商户号
			requiredParameters.put(MCH_ID, document.createXPath(root.getPath() + "/" + MCH_ID).selectSingleNode(document).getText());
			// 随机字符串
			requiredParameters.put(NONCE_STR, document.createXPath(root.getPath() + "/" + NONCE_STR).selectSingleNode(document).getText());
			// 签名
			requiredParameters.put(SIGN, document.createXPath(root.getPath() + "/" + SIGN).selectSingleNode(document).getText());
			// 业务结果
			requiredParameters.put(RESULT_CODE, document.createXPath(root.getPath() + "/" + RESULT_CODE).selectSingleNode(document).getText());
			// 用户标识
			requiredParameters.put(OPENID, document.createXPath(root.getPath() + "/" + OPENID).selectSingleNode(document).getText());
			// 交易类型
			requiredParameters.put(TRADE_TYPE, document.createXPath(root.getPath() + "/" + TRADE_TYPE).selectSingleNode(document).getText());
			// 付款银行
			requiredParameters.put(BANK_TYPE, document.createXPath(root.getPath() + "/" + BANK_TYPE).selectSingleNode(document).getText());
			// 总金额
			requiredParameters.put(TOTAL_FEE, document.createXPath(root.getPath() + "/" + TOTAL_FEE).selectSingleNode(document).getText());
			// 现金支付金额
			requiredParameters.put(CASH_FEE, document.createXPath(root.getPath() + "/" + CASH_FEE).selectSingleNode(document).getText());
			// 微信支付订单号
			requiredParameters.put(TRANSACTION_ID, document.createXPath(root.getPath() + "/" + TRANSACTION_ID).selectSingleNode(document).getText());
			// 商户订单号
			requiredParameters.put(OUT_TRADE_NO, document.createXPath(root.getPath() + "/" + OUT_TRADE_NO).selectSingleNode(document).getText());
			// 支付完成时间
			requiredParameters.put(TIME_END, document.createXPath(root.getPath() + "/" + TIME_END).selectSingleNode(document).getText());
		}
		return requiredParameters;
	}
	
	/**
	 * 解析出通知中的非必要参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param document
	 * @param root
	 * @return
	 */
	protected Map<String, String> parseNotifyUnrequiredParameters(Document document, Element root, String returnCode) {
		Map<String, String> unrequiredParameters = MapUtils.newHashMap();
		
		// 返回信息
		Node returnMsgNode = document.createXPath(root.getPath() + "/" + RETURN_MSG).selectSingleNode(document);
		if (returnMsgNode != null)
			unrequiredParameters.put(RETURN_MSG, returnMsgNode.getText());
		else
			unrequiredParameters.put(RETURN_MSG, "");
		
		if (ReturnCode.SUCCESS.getCode().equals(returnCode)) {
			// 设备号
			Node deviceInfoNode = document.createXPath(root.getPath() + "/" + DEVICE_INFO).selectSingleNode(document);
			if (deviceInfoNode != null)
				unrequiredParameters.put(DEVICE_INFO, deviceInfoNode.getText());
			
			// 错误代码
			Node errCodeNode = document.createXPath(root.getPath() + "/" + ERR_CODE).selectSingleNode(document);
			if (errCodeNode != null)
				unrequiredParameters.put(ERR_CODE, errCodeNode.getText());
			
			// 错误代码描述
			Node errCodeDesNode = document.createXPath(root.getPath() + "/" + ERR_CODE_DES).selectSingleNode(document);
			if (errCodeDesNode != null)
				unrequiredParameters.put(ERR_CODE_DES, errCodeDesNode.getText());
			
			// 是否关注公众账号
			Node isSubscribeNode = document.createXPath(root.getPath() + "/" + IS_SUBSCRIBE).selectSingleNode(document);
			if (isSubscribeNode != null)
				unrequiredParameters.put(IS_SUBSCRIBE, isSubscribeNode.getText());
			
			// 货币种类
			Node feeTypeNode = document.createXPath(root.getPath() + "/" + FEE_TYPE).selectSingleNode(document);
			if (feeTypeNode != null)
				unrequiredParameters.put(FEE_TYPE, feeTypeNode.getText());
			
			// 现金支付货币类型
			Node cashFeeTypeNode = document.createXPath(root.getPath() + "/" + CASH_FEE_TYPE).selectSingleNode(document);
			if (cashFeeTypeNode != null)
				unrequiredParameters.put(CASH_FEE_TYPE, cashFeeTypeNode.getText());
			
			// 代金券或立减优惠金额 
			Node couponFeeNode = document.createXPath(root.getPath() + "/" + COUPON_FEE).selectSingleNode(document);
			if (couponFeeNode != null)
				unrequiredParameters.put(COUPON_FEE, couponFeeNode.getText());
			
			// 代金券或立减优惠使用数量
			Node couponCountNode = document.createXPath(root.getPath() + "/" + COUPON_COUNT).selectSingleNode(document);
			if (couponCountNode != null)
				unrequiredParameters.put(COUPON_COUNT, couponCountNode.getText());
			
			// 代金券或立减优惠ID 
//			Node couponId$nNode = document.createXPath(root.getPath() + "/" + COUPON_ID_$N).selectSingleNode(document);
//			if (couponId$nNode != null)
//				unrequiredParameters.put(COUPON_ID_$N, couponId$nNode.getText());
			
			// 单个代金券或立减优惠支付金额
//			Node couponFee$nNode = document.createXPath(root.getPath() + "/" + COUPON_FEE_$N).selectSingleNode(document);
//			if (couponFee$nNode != null)
//				unrequiredParameters.put(COUPON_FEE_$N, couponFee$nNode.getText());
			
			// 商家数据包 	
			Node attachNode = document.createXPath(root.getPath() + "/" + ATTACH).selectSingleNode(document);
			if (attachNode != null)
				unrequiredParameters.put(ATTACH, attachNode.getText());
		}
		
		return unrequiredParameters;
	}
	
}
