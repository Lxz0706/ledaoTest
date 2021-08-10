package com.ledao.common.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.utils.http.CommonUtil;

@Component
public class JmsConsumer {
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");

	@JmsListener(destination = "zyQueue1")
	public void receiveMessage1(String text) {
		System.out.println("1接收队列消息时间：" + df.format(new Date()) + ", 接收到消息内容:" + text);
	}

	@JmsListener(destination = "zyQueueCommon")
	public void receiveMessageCommon(String dataStr) {
		List<TemplateParam> templateParamList = new ArrayList<>();
		try {
			templateParamList = JSONObject.parseObject(dataStr,List.class);
			// 获取维修主管的openId
			String toUserOpenId = "";
			Template tem = new Template();
//			所需下发的模板消息的id
			tem.setTemplateId(WeChatConstants.WECHATCUMPLATEIDTOTEACHER);
//			表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
			tem.setFormId("");
			tem.setTopColor("#00DD00");
//			接收者（用户）的 openid
			tem.setToUser(toUserOpenId);
//			详情页
			tem.setPage(WeChatConstants.WECHATIINDEX);

//			tem.setTemplateParamList(templateParamList);
//			boolean result = sendTemplateMsg(tem);
//			System.out.println("推送结果："+result);
			System.out.println("jieguo "+dataStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendTemplateMsg(Template template) {
		String token = getToken(template);
		boolean flag = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
		String jsonString = template.toJSON();
		JSONObject jsonResult = CommonUtil.httpsRequest(requestUrl, "POST", jsonString);
		if (jsonResult != null) {
			int errorCode = jsonResult.getIntValue("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				flag = true;
			} else {
				System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 获取token
	 * @param template
	 * @return
	 */
    public  String getToken(Template template){  
//        String requestUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx885aa2a84dbe54a7&secret=d1a4da55b133bffd04acff970b3990a4";  
        String requestUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeChatConstants.WXAPPID+"&secret="+WeChatConstants.WXSECRET;  
        JSONObject jsonResult=CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());  
        if(jsonResult!=null){  
            String access_token=jsonResult.getString("access_token");  
            return access_token;
        }else{  
        	  return "";     
        }    
    }
}
