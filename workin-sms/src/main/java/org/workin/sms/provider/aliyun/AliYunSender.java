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
 * Create Date : 2015-12-28
 */

package org.workin.sms.provider.aliyun;

import java.util.Date;
import java.util.Map;

import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.json.util.JSONUtils;
import org.workin.sms.AbstractSender;
import org.workin.sms.packet.MessagePacket;
import org.workin.sms.packet.TemplateMessagePacket;
import org.workin.spring.context.ApplicationContextParameter;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里大鱼短信发送实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class AliYunSender extends AbstractSender {
	
	@Override
	public CodeMessageModel send(MessagePacket packet) throws Exception {
		AssertUtils.assertTrue(packet instanceof TemplateMessagePacket,
				"Aliyun sended message packet must be instance of " + TemplateMessagePacket.class.getName());
				
		CodeMessageModel model = new CodeMessageModel();
		TemplateMessagePacket sendedPacket = (TemplateMessagePacket) packet;
		
		TaobaoClient taobaoClient = new DefaultTaobaoClient(parameters.get("url"), 
				parameters.get("appKey"), parameters.get("appSecret"));
		AlibabaAliqinFcSmsNumSendRequest request = new AlibabaAliqinFcSmsNumSendRequest();
		
		/* 设置公共请求参数 */
		request.setTimestamp(new Date().getTime());
		
		/* 设置请求参数 */
		request.setSmsType(parameters.get("smsType"));
		request.setSmsFreeSignName(sendedPacket.getSignature());
		request.setSmsParamString(JSONUtils.toString(sendedPacket.getParameters()));
		request.setRecNum(sendedPacket.getPhones());
		request.setSmsTemplateCode(sendedPacket.getTemplateId());
		
		AlibabaAliqinFcSmsNumSendResponse response = taobaoClient.execute(request);
		BizResult result = response.getResult();
		if (!result.getSuccess()) {
			model.setCode(SystemStatus.FAILED.getKey());
			// 在阿里云错误码前加上固定的前缀标识后返回
			model.setMessage("msg.error.aliyun." + result.getErrCode());
		}
		return model;
	}

	@Override
	protected Map<String, String> initParameters(ApplicationContextParameter<String, Object> smsContextParameters) {
		Map<String, String> parameters = MapUtils.newHashMap();
		String url = smsContextParameters.getValue("aliyun.sms.url", String.class);
		if (StringUtils.isBlank(url))
			url = "http://gw.api.taobao.com/router/rest";
		
		String appKey = smsContextParameters.getValue("aliyun.sms.app.key", String.class);
		if (StringUtils.isBlank(appKey))
			throw new IllegalArgumentException("Aliyun sms app key must not be null or blank.");
		
		String appSecret = smsContextParameters.getValue("aliyun.sms.app.secret", String.class);
		if (StringUtils.isBlank(appSecret))
			throw new IllegalArgumentException("Aliyun sms app secret must not be null or blank.");
		
		String smsType = smsContextParameters.getValue("aliyun.sms.type", String.class);
		if (StringUtils.isBlank(smsType))
			smsType = "normal";
		
		parameters.put("url", url);
		parameters.put("appKey", appKey);
		parameters.put("appSecret", appSecret);
		parameters.put("smsType", smsType);
		return parameters;
	}

}
