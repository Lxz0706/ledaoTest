package com.ledao.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.http.HttpUtils;

/**
 * @ClassName DingTalkUtil
 * @Description TODO
 * @Author 87852
 * @Date 2021/7/13 10:04
 * @Version 1.0
 */
public class DingTalkUtil {

    /**
     * 获取钉钉的accessToken
     *
     * @param url
     * @param corpid
     * @param secret
     * @return
     */
    public static String getAccessToken(String url, String corpid, String secret) {
        String requestUrl = url + "?appkey=" + corpid + "&appsecret=" + secret;
        String result = HttpUtils.sendGet(requestUrl, "");
        String accessToken = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(result);
        String msg = (String) jsonObject.get("errmsg");
        if ("ok".equals(msg)) {
            accessToken = (String) jsonObject.get("access_token");
        }
        return accessToken;
    }

    public static JSONArray getCardList(String accessToken, String workDateFrom, String workDateTo, String offset, String limit) {
        String recordUrl = "https://oapi.dingtalk.com/attendance/list?access_token=" + accessToken;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workDateFrom", workDateFrom);
        jsonObject.put("workDateTo", workDateTo);
        jsonObject.put("offset", offset);
        jsonObject.put("limit", limit);
        String result = HttpUtils.sendPost(recordUrl, jsonObject.toString());
        JSONObject resutJSON = JSONObject.parseObject(result);
        String msg = (String) resutJSON.get("errmsg");
        JSONArray jsonArray = null;
        if ("ok".equals(msg)) {
            jsonArray = (JSONArray) resutJSON.get("recordresult");
        }
        return jsonArray;
    }
}
