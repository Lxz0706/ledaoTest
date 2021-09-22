package com.ledao.common.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.http.CommonUtil;
import com.ledao.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class WechatMessageUtil {

    /**
     * 获取公众号access_token
     *
     * @return
     */
   /* private String getAccessToken() {
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appId);
        params.add("secret", secret);
        params.add("grant_type", "client_credential");
        URI getAccessToken = HttpUtils.getURIwithParams(getAccessTokenUrl, params);
        return this.restTemplate.exchange(getAccessToken, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
    }*/

    /**
     * 获取公众号关注用户的openid
     *
     * @param accessToken
     * @param nextOpenId
     * @return
     */
    public static JSONObject getAllUser(String accessToken, String nextOpenId) {
        /*String getAllUserUrl = "https://api.weixin.qq.com/cgi-bin/user/get";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        if (!nextOpenId.equals("")) {
            params.add("next_openid", nextOpenId);
        }*/
        /*URI getAllUser = HttpUtils.getURIwithParams(getAllUserUrl, params);
        return this.restTemplate.exchange(getAllUser, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();*/
        /*JSONObject jsonResult = CommonUtil.httpsRequest(url, "POST", com.alibaba.fastjson.JSONObject.toJSON(template).toString());
        return "";**/
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        String url = tmpurl.replace("ACCESS_TOKEN", accessToken);
        String fulUrl = url.replace("NEXT_OPENID",nextOpenId);
        JSONObject jsonResult = CommonUtil.httpsRequest(fulUrl, "GET", null);
        return jsonResult;
    }


    public static void batchGetUserUnionId(String accessToken, List<String> openIds) {
        String batchUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
        String url = batchUrl.replace("ACCESS_TOKEN", accessToken);

       /* MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        URI batchUri = HttpUtils.getURIwithParams(batchUrl, params);*/
        JSONObject request = new JSONObject();
        JSONArray openidList = new JSONArray();

        int cout = openIds.size()/100;
        for(int j=0;j<cout;j++){
            for (int i=0;i<100;i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("lang", "zh_CN");
                jsonObject.put("openid", openIds.get(100*j+i));
                openidList.add(jsonObject);
            }
            request.put("user_list", openidList);
            JSONObject jsonResult = CommonUtil.wxMessageModeSendUrl(request, url);
            JSONArray data = (JSONArray) jsonResult.get("user_info_list");
            for(int i=0;i<data.size();i++) {
                String openid = (String) data.getJSONObject(i).get("openid");
                String unionid = (String) data.getJSONObject(i).get("unionid");
            }
        }

       /* for (String openId : openIds) {
            if (cout==100){
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lang", "zh_CN");
            jsonObject.put("openid", openId);
            openidList.add(jsonObject);
            cout++;
        }*/

//        WxUsersFromServer users = this.restTemplate.postForObject(batchUri, request, WxUsersFromServer.class);
//        if (users != null) {
            /*users.getUser_info_list().forEach(item -> {
                mapper.updateMpWxUser(WxUserInfo.builder().openId(item.getOpenid()).unionId(item.getUnionid()).build());
            });*/
//        }
       /* Integer isNullTotal = mapper.countWhenUnionIsNull();
        if (isNullTotal > 0) {
            openIds = mapper.selectAllOpenIdWhenUnionIsNull();
            if (openIds.size() > 0) {
                batchGetUserUnionId(accessToken, openIds);
            }
        }*/
    }
}