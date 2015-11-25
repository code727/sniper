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

package org.workin.payment.provider.wechatpay.parser;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.IOUtils;
import org.workin.commons.util.MapUtils;
import org.workin.payment.provider.wechatpay.enums.ResultCode;
import org.workin.payment.provider.wechatpay.enums.ReturnCode;
import org.workin.payment.provider.wechatpay.enums.TradeType;

/**
 * @description 微信支付解析器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultWechatpayParser implements WechatpayParser {
	
	@Override
	public ResultModel<Map<String, Object>> parsePlaceOrderResult(String result) {
		ResultModel<Map<String, Object>> resultModel = new ResultModel<Map<String,Object>>();
		try {
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			// 返回状态码
			String returnCode = document.createXPath(root.getPath() + "/" + RETURN_CODE).selectSingleNode(document).getText();
			if (ReturnCode.SUCCESS.equals(returnCode)) {
				// 业务结果码
				String resultCode = document.createXPath(root.getPath() + "/" + RESULT_CODE).selectSingleNode(document).getText();
				if (ResultCode.SUCCESS.equals(resultCode)) {
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
							
					resultModel.setDate(paymentRequestParameters);
				} else {
					/* result_code(业务码)未成功时，则返回错误代码 */
					resultModel.setCode(document.createXPath(root.getPath() + "/" + ERR_CODE).selectSingleNode(document).getText());
				}
			} else {
				/* return_code未成功时，则返回失败状态，并且消息即为return_msg的值 */
				resultModel.setCode(SystemStatus.FAILED.getKey());
				resultModel.setMessage(document.createXPath(root.getPath() + "/" + RETURN_MSG).selectSingleNode(document).getText());
			}
		}  catch (DocumentException e) {
			resultModel.setCode(SystemStatus.FAILED.getKey());
			resultModel.setMessage("msg.error.parse.wechatpay.placeorder.exception");
			e.printStackTrace();
		}
		return resultModel;
	}

	@Override
	public Map<String, Object> parseNotifyRequest(HttpServletRequest request) throws Exception {
		Document document = DocumentHelper.parseText(IOUtils.read(request.getInputStream()));
		Element root = document.getRootElement();
		Map<String, Object> requestParameters = MapUtils.newHashMap();
		requestParameters.putAll(parseNotifyRequiredParameters(document, root));
		requestParameters.putAll(parseNotifyUnrequiredParameters(document, root, requestParameters.get("return_code").toString()));
		return requestParameters;
	}
	
	/**
	 * @description 解析出通知中的必要参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param document
	 * @param root
	 * @return
	 */
	protected Map<String, Object> parseNotifyRequiredParameters(Document document, Element root) {
		Map<String, Object> requiredParameters = MapUtils.newHashMap();
		String returnCode = document.createXPath(root.getPath() + "/" + RETURN_CODE).selectSingleNode(document).getText();
		requiredParameters.put(RETURN_CODE, returnCode);
		if (ReturnCode.SUCCESS.equals(returnCode)) {
			requiredParameters.put(APPID, document.createXPath(root.getPath() + "/" + APPID).selectSingleNode(document).getText());
			requiredParameters.put(MCH_ID, document.createXPath(root.getPath() + "/" + MCH_ID).selectSingleNode(document).getText());
			requiredParameters.put(NONCE_STR, document.createXPath(root.getPath() + "/" + NONCE_STR).selectSingleNode(document).getText());
			requiredParameters.put(SIGN, document.createXPath(root.getPath() + "/" + SIGN).selectSingleNode(document).getText());
			requiredParameters.put(RESULT_CODE, document.createXPath(root.getPath() + "/" + RESULT_CODE).selectSingleNode(document).getText());
			
			requiredParameters.put(OPENID, document.createXPath(root.getPath() + "/" + OPENID).selectSingleNode(document).getText());
			requiredParameters.put(TRADE_TYPE, document.createXPath(root.getPath() + "/" + TRADE_TYPE).selectSingleNode(document).getText());
			requiredParameters.put(BANK_TYPE, document.createXPath(root.getPath() + "/" + BANK_TYPE).selectSingleNode(document).getText());
			requiredParameters.put(TOTAL_FEE, document.createXPath(root.getPath() + "/" + TOTAL_FEE).selectSingleNode(document).getText());
			requiredParameters.put(CASH_FEE, document.createXPath(root.getPath() + "/" + CASH_FEE).selectSingleNode(document).getText());
			requiredParameters.put(TRANSACTION_ID, document.createXPath(root.getPath() + "/" + TRANSACTION_ID).selectSingleNode(document).getText());
			requiredParameters.put(OUT_TRADE_NO, document.createXPath(root.getPath() + "/" + OUT_TRADE_NO).selectSingleNode(document).getText());
			requiredParameters.put(TIME_END, document.createXPath(root.getPath() + "/" + TIME_END).selectSingleNode(document).getText());
		}
		return requiredParameters;
	}
	
	/**
	 * @description 解析出通知中的非必要参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param document
	 * @param root
	 * @return
	 */
	protected Map<String, Object> parseNotifyUnrequiredParameters(Document document, Element root, String returnCode) {
		Map<String, Object> unrequiredParameters = MapUtils.newHashMap();
		
		// 返回信息
		Node returnMsgNode = document.createXPath(root.getPath() + "/" + RETURN_MSG).selectSingleNode(document);
		if (returnMsgNode != null)
			unrequiredParameters.put(RETURN_MSG, returnMsgNode.getText());
		
		if (ReturnCode.SUCCESS.equals(returnCode)) {
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
			Node couponId$nNode = document.createXPath(root.getPath() + "/" + COUPON_ID_$N).selectSingleNode(document);
			if (couponId$nNode != null)
				unrequiredParameters.put(COUPON_ID_$N, couponId$nNode.getText());
			
			// 单个代金券或立减优惠支付金额
			Node couponFee$nNode = document.createXPath(root.getPath() + "/" + COUPON_FEE_$N).selectSingleNode(document);
			if (couponFee$nNode != null)
				unrequiredParameters.put(COUPON_FEE_$N, couponFee$nNode.getText());
			
			// 商家数据包 	
			Node attachNode = document.createXPath(root.getPath() + "/" + ATTACH).selectSingleNode(document);
			if (attachNode != null)
				unrequiredParameters.put(ATTACH, attachNode.getText());
		}
		
		return unrequiredParameters;
	}
	
}
