package com.ledao.web.controller.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.message.Template;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.http.CommonUtil;
import com.ledao.common.utils.qrCode.HttpUtil;
import com.ledao.system.dao.SysSendMsgLog;
import com.ledao.system.service.ISysSendMsgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
public class JmsConsumer {

    public static String token = "";
    public static Date onTime = null;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");

    @Autowired
    private ISysSendMsgLogService iSysSendMsgLogService;

    @JmsListener(destination = "zyQueue1")
    public void receiveMessage1(String text) {
        System.out.println("1接收队列消息时间：" + df.format(new Date()) + ", 接收到消息内容:" + text);
    }

    @JmsListener(destination = "zyQueueCommon")
    public void receiveMessageCommon(String dataStr) {

        JSONObject data = JSONObject.parseObject(dataStr);
        if (StringUtils.isNotNull(data)) {
            System.out.println("send msg: " + data.toJSONString());
        }
        String accessToken = data.getString("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("zyQueueCommon：获取accessToken为空，无法进行消息推送");
            return;
        }
        // 封装要发送的json
        Map<String, Object> map = new HashMap();
        //你要发送给某个用户的openid 前提是已关注该公众号,该openid是对应该公众号的，不是普通的openid
        map.put("touser", data.getString("toUser"));
        map.put("template_id", WeChatConstants.WXMSGWORKFLOW_TEMPID);//模板消息id
        //map.put("url","https://www.vipkes.cn");//用户点击模板消息，要跳转的地址
        // 封装miniprogram 跳转小程序用,不跳不要填
        TreeMap<String, String> mapA = new TreeMap<>();
        mapA.put("appid", WeChatConstants.WXAPPID); //小程序appid
        mapA.put("pagepath", data.getString("pagepath")); //小程序页面pagepath
        map.put("miniprogram", mapA);

        // 以下就是根据模板消息的格式封装好，我模板的是：问题反馈结果通知  可以和我一样试试
        // 封装first
        String wordColor = "#03060f";
        Map firstMap = new HashMap();
        firstMap.put("value", data.getString("first")); //内容
        firstMap.put("color", wordColor); //字体颜色

        // 封装keyword1 提交的问题
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", data.getString("word1"));
        keyword1Map.put("color", wordColor);

        // 封装keyword2此处也可以是商品名
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", data.getString("word2"));
        keyword2Map.put("color", wordColor);

        // 封装keyword2此处可以是商品价格
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword3Map = new HashMap();
        if (StringUtils.isNotNull(data.getDate("word3"))) {
            keyword3Map.put("value", simpleDateFormat.format(data.getDate("word3")));
        } else {
            keyword3Map.put("value", "");
        }
        keyword3Map.put("color", wordColor);

        // 封装keyword4
        // 封装remark
        Date date = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword4Map = new HashMap();
        keyword4Map.put("value", simpleDateFormat.format(new Date()));
        keyword4Map.put("color", wordColor);


        Map remarkMap = new HashMap();
        remarkMap.put("value", data.getString("word5"));
        remarkMap.put("color", wordColor);

        // 封装data
        Map dataMap = new HashMap();
        dataMap.put("first", firstMap);
        dataMap.put("keyword1", keyword1Map);
        dataMap.put("keyword2", keyword2Map);
        dataMap.put("keyword3", keyword3Map);
        dataMap.put("remark", remarkMap);

        map.put("data", dataMap);
        String r = HttpUtil.getJsonData(JSONObject.parseObject(JSON.toJSONString(map)), "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken); //发送模板消息，这里有个工具类，我给你哟
        System.out.println("-->" + r);
        try {
            SysSendMsgLog sendLog = new SysSendMsgLog();
            sendLog.setToUser(data.getString("toUser"));
            sendLog.setSendMsg(data.toJSONString());
            sendLog.setResMsg(r);
            sendLog.setCreateTime(new Date());
            iSysSendMsgLogService.insertSysSendMsgLog(sendLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @JmsListener(destination = "ThQueueCommon")
    public void thReceiveMessageCommon(String dataStr) {
        System.out.println("============发送信息：" + dataStr + "================");
        JSONObject data = JSONObject.parseObject(dataStr);
        if (StringUtils.isNotNull(data)) {
            System.out.println("send msg: " + data.toJSONString());
        }
        String accessToken = data.getString("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("ThQueueCommon：获取accessToken为空，无法进行消息推送");
            return;
        }

        // 封装要发送的json
        Map<String, Object> map = new HashMap();
        map.put("touser", data.getString("toUser"));//你要发送给某个用户的openid 前提是已关注该公众号,该openid是对应该公众号的，不是普通的openid
        map.put("template_id", WeChatConstants.WXMSGTASK_TEMPID);//模板消息id
        //map.put("url","https://www.vipkes.cn");//用户点击模板消息，要跳转的地址
        // 封装miniprogram 跳转小程序用,不跳不要填
        Map<String, String> mapA = new HashMap<>();
        mapA.put("appid", WeChatConstants.WXAPPID); //小程序appid
        mapA.put("pagepath", data.getString("pagepath")); //小程序页面pagepath
        //map.put("miniprogram", mapA);

        // 以下就是根据模板消息的格式封装好，我模板的是：问题反馈结果通知  可以和我一样试试
        // 封装first
        String wordColor = "#03060f";
        Map firstMap = new HashMap();
        firstMap.put("value", data.getString("first")); //内容
        firstMap.put("color", wordColor); //字体颜色

        // 封装keyword1 提交的问题
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", data.getString("word1"));
        keyword1Map.put("color", wordColor);

        // 封装keyword2此处也可以是商品名
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", data.getString("word2"));
        keyword2Map.put("color", wordColor);

        // 封装keyword2此处可以是商品价格
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword3Map = new HashMap();
        keyword3Map.put("value", data.getString("word3"));
        keyword3Map.put("color", wordColor);

        // 封装keyword4
        // 封装remark
        Date date = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword4Map = new HashMap();
        keyword4Map.put("value", data.getString("word4"));
        keyword4Map.put("color", wordColor);


        Map remarkMap = new HashMap();
        remarkMap.put("value", data.getString("word5"));
        remarkMap.put("color", wordColor);

        // 封装data
        Map dataMap = new HashMap();
        dataMap.put("first", firstMap);
        dataMap.put("keyword1", keyword1Map);
        dataMap.put("keyword2", keyword2Map);
        dataMap.put("keyword3", keyword3Map);
        dataMap.put("keyword4", keyword4Map);
        dataMap.put("remark", remarkMap);

        map.put("data", dataMap);
        String r = HttpUtil.getJsonData(JSONObject.parseObject(JSON.toJSONString(map)), "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken); //发送模板消息，这里有个工具类，我给你哟
        System.out.println("-->" + r);
        try {
            SysSendMsgLog sendLog = new SysSendMsgLog();
            sendLog.setToUser(data.getString("toUser"));
            sendLog.setSendMsg(data.toJSONString());
            sendLog.setResMsg(r);
            sendLog.setCreateTime(new Date());
            iSysSendMsgLogService.insertSysSendMsgLog(sendLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JmsListener(destination = "ThQueueCommonUsal")
    public void thReceiveMessageCommonUsal(String dataStr) {
        JSONObject data = JSONObject.parseObject(dataStr);
        if (StringUtils.isNotNull(data)) {
            System.out.println("send msg: " + data.toJSONString());
        }
        String accessToken = data.getString("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("ThQueueCommon：获取accessToken为空，无法进行消息推送");
            return;
        }
        // 封装要发送的json
        Map<String, Object> map = new HashMap();
        map.put("touser", data.getString("toUser"));//你要发送给某个用户的openid 前提是已关注该公众号,该openid是对应该公众号的，不是普通的openid
        map.put("template_id", WeChatConstants.WXMSGTASKUSAL_TEMPID);//模板消息id
//		map.put("url",data.getString("url"));//用户点击模板消息，要跳转的地址
        // 封装miniprogram 跳转小程序用,不跳不要填
        Map<String, String> mapA = new HashMap<>();
        mapA.put("appid", WeChatConstants.WXAPPID); //小程序appid
        mapA.put("pagepath", data.getString("pagepath")); //小程序页面pagepath
        map.put("miniprogram", mapA);

        // 以下就是根据模板消息的格式封装好，我模板的是：问题反馈结果通知  可以和我一样试试
        // 封装first
        String wordColor = "#03060f";
        Map firstMap = new HashMap();
        firstMap.put("value", data.getString("first")); //内容
        firstMap.put("color", wordColor); //字体颜色

        // 封装keyword1 提交的问题
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", data.getString("word1"));
        keyword1Map.put("color", wordColor);

        // 封装keyword2此处也可以是商品名
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", data.getString("word2"));
        keyword2Map.put("color", wordColor);

        // 封装keyword2此处可以是商品价格
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword3Map = new HashMap();
        keyword3Map.put("value", data.getString("word3"));
        keyword3Map.put("color", wordColor);

        // 封装keyword4
        // 封装remark
        Date date = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword4Map = new HashMap();
        keyword4Map.put("value", data.getString("word4"));
        keyword4Map.put("color", wordColor);


        Map remarkMap = new HashMap();
        remarkMap.put("value", data.getString("word5"));
        remarkMap.put("color", wordColor);

        // 封装data
        Map dataMap = new HashMap();
        dataMap.put("first", firstMap);
        dataMap.put("keyword1", keyword1Map);
        dataMap.put("keyword2", keyword2Map);
        dataMap.put("keyword3", keyword3Map);
        dataMap.put("keyword4", keyword4Map);
        dataMap.put("remark", remarkMap);

        map.put("data", dataMap);
        String r = HttpUtil.getJsonData(JSONObject.parseObject(JSON.toJSONString(map)), "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken); //发送模板消息，这里有个工具类，我给你哟
        System.out.println("-->" + r);
        try {
            SysSendMsgLog sendLog = new SysSendMsgLog();
            sendLog.setToUser(data.getString("toUser"));
            sendLog.setSendMsg(data.toJSONString());
            sendLog.setResMsg(r);
            sendLog.setCreateTime(new Date());
            iSysSendMsgLogService.insertSysSendMsgLog(sendLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "dailyReceiveMessageCommon")
    public void dailyReceiveMessageCommon(String dataStr) {
        JSONObject data = JSONObject.parseObject(dataStr);
        if (StringUtils.isNotNull(data)) {
            System.out.println("send msg: " + data.toJSONString());
        }
        String accessToken = data.getString("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("ThQueueCommon：获取accessToken为空，无法进行消息推送");
            return;
        }
        // 封装要发送的json
        Map<String, Object> map = new HashMap();
        //你要发送给某个用户的openid 前提是已关注该公众号,该openid是对应该公众号的，不是普通的openid
        map.put("touser", data.getString("toUser"));
        //模板消息id
        map.put("template_id", WeChatConstants.WXMSGTASKUSAL_TEMPID);
        //用户点击模板消息，要跳转的地址
//		map.put("url",data.getString("url"));
        // 封装miniprogram 跳转小程序用,不跳不要填
        Map<String, String> mapA = new HashMap<>();
        //小程序appid
        mapA.put("appid", WeChatConstants.WXAPPID);
        //小程序页面pagepath
        mapA.put("pagepath", data.getString("pagepath"));
        map.put("miniprogram", mapA);

        // 以下就是根据模板消息的格式封装好，我模板的是：问题反馈结果通知  可以和我一样试试
        // 封装first
        String wordColor = "#03060f";
        Map firstMap = new HashMap();
        //内容
        firstMap.put("value", data.getString("first"));
        //字体颜色
        firstMap.put("color", wordColor);

        // 封装keyword1 提交的问题
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", data.getString("word1"));
        keyword1Map.put("color", wordColor);

        // 封装keyword2此处也可以是商品名
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", data.getString("word2"));
        keyword2Map.put("color", wordColor);

        // 封装keyword2此处可以是商品价格
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map keyword3Map = new HashMap();
        keyword3Map.put("value", data.getString("word3"));
        keyword3Map.put("color", wordColor);

        // 封装keyword4
        // 封装remark
        Date date = new Date();
        Map keyword4Map = new HashMap();
        keyword4Map.put("value", data.getString("word4"));
        keyword4Map.put("color", wordColor);


        Map remarkMap = new HashMap();
        remarkMap.put("value", data.getString("word5"));
        remarkMap.put("color", wordColor);

        // 封装data
        Map dataMap = new HashMap();
        dataMap.put("first", firstMap);
        dataMap.put("keyword1", keyword1Map);
        dataMap.put("keyword2", keyword2Map);
        dataMap.put("keyword3", keyword3Map);
        dataMap.put("keyword4", keyword4Map);
        dataMap.put("remark", remarkMap);

        map.put("data", dataMap);
        String r = HttpUtil.getJsonData(JSONObject.parseObject(JSON.toJSONString(map)), "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken); //发送模板消息，这里有个工具类，我给你哟
        System.out.println("-->" + r);
        try {
            SysSendMsgLog sendLog = new SysSendMsgLog();
            sendLog.setToUser(data.getString("toUser"));
            sendLog.setSendMsg(data.toJSONString());
            sendLog.setResMsg(r);
            sendLog.setCreateTime(new Date());
            iSysSendMsgLogService.insertSysSendMsgLog(sendLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	@JmsListener(destination = "DailyQueueCommon")
//	public void dailyReceiveMessageCommon(String dataStr) {
//		JSONObject data = JSONObject.parseObject(dataStr);
//		if (StringUtils.isNotNull(data)){
//			System.out.println("send msg: "+data.toJSONString());
//		}
//		List<TemplateParam> templateParamList = new ArrayList<>();
//		try {
//			String access_token = getAccessToken(WeChatConstants.WXAPPID,WeChatConstants.WXSECRET);
//			System.out.println(access_token);
//			Template template = new Template();
////			template.setTemplate_id("WOGdq_xCeS-r6cOuTe-vxrLy9CeDW29wTpAb1AlNJj4");
////			template.setTouser("oQsTX5eQXxNisA05sgl2OfG9vCIM");
//			template.setTemplate_id(WeChatConstants.WXMSGTASK_TEMPID);
//			template.setTouser(data.getString("toUser"));
//
//			Map<String, TemplateData> paras = new HashMap<>(4);
//			paras.put("thing16", new TemplateData((String) data.get("thing16")));
//			paras.put("thing20", new TemplateData((String) data.get("thing20")));
//			paras.put("time4", new TemplateData((String) data.get("time4")));
//			paras.put("thing1", new TemplateData((String) data.get("thing1")));
//			paras.put("phrase7", new TemplateData((String) data.get("phrase7")));
//			template.setData(paras);
//			System.out.println(paras.toString());
//			ModeUtil.wxMessageModeSendUrl(template, access_token);
//			System.out.println("发送成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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


    public String getToken(Template template) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatConstants.WXAPPID + "&secret=" + WeChatConstants.WXSECRET;
        JSONObject jsonResult = CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());
        if (jsonResult != null) {
            String access_token = jsonResult.getString("access_token");
            return access_token;
        } else {
            return "";
        }
    }

    public static String getAccess_token(String appId, String appSecret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        String accessToken = null;
        JSONObject jsonObj = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 必须是get方式请求
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            accessToken = new String(jsonBytes, "UTF-8");
            System.err.println(accessToken);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String getAccessToken(String APPID, String APPSECRET) throws Exception {
        long newTime = DateUtils.getNowDate().getTime();
        if (JmsConsumer.onTime != null && (newTime - JmsConsumer.onTime.getTime()) / 1000 / 60 < 90) {
            return JmsConsumer.token;
        }
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID
                + "&secret=" + APPSECRET;
        System.out.println("URL for getting accessToken accessTokenUrl=" + accessTokenUrl);
        URL url = new URL(accessTokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        // 获取返回的字符
        InputStream inputStream = connection.getInputStream();
        int size = inputStream.available();
        byte[] bs = new byte[size];
        inputStream.read(bs);
        String message = new String(bs, "UTF-8");
        // 获取access_token
        JSONObject jsonObject = JSONObject.parseObject(message);
        String accessToken = jsonObject.getString("access_token");
        JmsConsumer.token = accessToken;
        return accessToken;
    }

    @JmsListener(destination = "bankruptcyPushMessageCommon")
    public void bankruptcyPushMessageCommon(String dataStr) {
        JSONObject data = JSONObject.parseObject(dataStr);
        if (StringUtils.isNotNull(data)) {
            System.out.println("send msg: " + data.toJSONString());
        }
        String accessToken = data.getString("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("ThQueueCommon：获取accessToken为空，无法进行消息推送");
            return;
        }

        // 封装要发送的json
        Map<String, Object> map = new HashMap();
        //你要发送给某个用户的openid 前提是已关注该公众号,该openid是对应该公众号的，不是普通的openid
        map.put("touser", data.getString("toUser"));
        //模板消息id
        map.put("template_id", WeChatConstants.WXMSGTASK_BANKRUPTCY_PUSH);
        // 封装miniprogram 跳转小程序用,不跳不要填
        Map<String, String> mapA = new HashMap<>();
        mapA.put("appid", WeChatConstants.WXAPPID); //小程序appid
        mapA.put("pagepath", data.getString("pagepath")); //小程序页面pagepath

        // 封装first
        String wordColor = "#03060f";
        Map firstMap = new HashMap();
        firstMap.put("value", data.getString("first")); //内容
        firstMap.put("color", wordColor); //字体颜色

        // 封装keyword1 提交的问题
        Map keyword1Map = new HashMap();
        keyword1Map.put("value", data.getString("word1"));
        keyword1Map.put("color", wordColor);

        // 封装keyword2此处也可以是商品名
        Map keyword2Map = new HashMap();
        keyword2Map.put("value", data.getString("word2"));
        keyword2Map.put("color", wordColor);

        // 封装keyword2此处可以是商品价格
        Map keyword3Map = new HashMap();
        keyword3Map.put("value", data.getString("word3"));
        keyword3Map.put("color", wordColor);

        // 封装keyword4
        Map keyword4Map = new HashMap();
        keyword4Map.put("value", data.getString("word4"));
        keyword4Map.put("color", wordColor);

        // 封装keyword4
        Map keyword5Map = new HashMap();
        keyword5Map.put("value", data.getString("word5"));
        keyword5Map.put("color", wordColor);

        // 封装remark
        Map remarkMap = new HashMap();
        remarkMap.put("value", data.getString("word6"));
        remarkMap.put("color", wordColor);

        // 封装data
        Map dataMap = new HashMap();
        dataMap.put("first", firstMap);
        dataMap.put("keyword1", keyword1Map);
        dataMap.put("keyword2", keyword2Map);
        dataMap.put("keyword3", keyword3Map);
        dataMap.put("keyword4", keyword4Map);
        dataMap.put("keyword5", keyword5Map);
        dataMap.put("remark", remarkMap);

        map.put("data", dataMap);
        String r = HttpUtil.getJsonData(JSONObject.parseObject(JSON.toJSONString(map)), "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken); //发送模板消息，这里有个工具类，我给你哟
        System.out.println("-->" + r);
        try {
            SysSendMsgLog sendLog = new SysSendMsgLog();
            sendLog.setToUser(data.getString("toUser"));
            sendLog.setSendMsg(data.toJSONString());
            sendLog.setResMsg(r);
            sendLog.setCreateTime(new Date());
            iSysSendMsgLogService.insertSysSendMsgLog(sendLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
