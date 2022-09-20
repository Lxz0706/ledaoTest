package com.ledao.common.utils.qrCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class WxQrCode {

    //获取AccessToken路径
    private static final String AccessToken_URL
            = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";//小程序id
    //获取二维码路径
    private static final String WxCode_URL
            = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";//小程序密钥


    //获取微信公众号关注用户
    private static final String USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";

    /**
     * 用于获取access_token
     *
     * @return access_token
     * @throws Exception
     */
    public static String getAccessToken(String appid, String secret) throws Exception {
        String requestUrl = AccessToken_URL.replace("APPID", appid).replace("APPSECRET", secret);
        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes("");
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        if (requestUrl.contains("nlp")) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        }
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        JSONObject jsonObject = JSON.parseObject(result);
        String accesstoken = jsonObject.getString("access_token");
        System.out.println("获取accesstoken返回数据信息：" + jsonObject);
        return accesstoken;
    }


    /*
     * 获取 二维码图片
     *
     */
    public static String getminiqrQr(String accessToken, String uploadPath, HttpServletResponse response, String parm) {
        JSONObject job = JSON.parseObject(parm);
        ServletOutputStream out = null;
        String ctxPath = uploadPath;
        String fileName = "twoCode.png";
        String bizPath = "files";
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String qrCode = bizPath + File.separator + nowday + File.separator + fileName;
        if (qrCode.contains("\\")) {
            qrCode = qrCode.replace("\\", "/");
        }
        try {
            String wxCodeURL = WxCode_URL.replace("ACCESS_TOKEN", accessToken);
            URL url = new URL(wxCodeURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpURLConnection.setRequestMethod("POST");
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", job.getString("scene"));
            String page = job.getString("url");
            if (StringUtils.isNotEmpty(page)) {
                //小程序暂未发布我没有带page参数
                paramJson.put("page", page);
            }
//            paramJson.put("page", "pages/mine/index"); //小程序暂未发布我没有带page参数
            paramJson.put("width", 150);
            paramJson.put("is_hyaline", true);
//            paramJson.put("trainId",trainId);

            //line_color生效
            paramJson.put("auto_color", false);
            JSONObject lineColor = new JSONObject();
            lineColor.put("r", 0);
            lineColor.put("g", 102);
            lineColor.put("b", 204);
            paramJson.put("line_color", lineColor);


            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
//            OutputStream os = new FileOutputStream(new File(savePath));
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = bis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCode;
    }

    public static String getWxQrCode(String accessToken, String uploadPath, String fileName, String param, HttpServletResponse response) {
        ServletOutputStream out = null;
        JSONObject job = JSON.parseObject(param);
        String bizPath = "files";
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        File file = new File(uploadPath + File.separator + bizPath + nowday);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String qrCode = uploadPath + File.separator + bizPath + File.separator + nowday + fileName + ".png";
        if (qrCode.contains("\\")) {
            qrCode = qrCode.replace("\\", "/");
        }
        try {
            String wxCodeURL = WxCode_URL.replace("ACCESS_TOKEN", accessToken);
            URL url = new URL(wxCodeURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpURLConnection.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            //这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("scene", job.getString("scene"));
            //注意该接口传入的是page而不是path
            paramJson.put("page", job.getString("url"));
            //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 200);
            paramJson.put("is_hyaline", true);
            //设置二维码颜色
            paramJson.put("auto_color", false);
            JSONObject lineColor = new JSONObject();
            lineColor.put("r", 0);
            lineColor.put("g", 102);
            lineColor.put("b", 204);
            paramJson.put("line_color", lineColor);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();

            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            //OutputStream os = new FileOutputStream(new File(savePath));
            out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = bis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            //os.close();
            out.flush();
            out.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCode;
    }


    /**
     * 下载微信小程序
     *
     * @param accessToken
     * @param uploadPath  文件根路径
     * @param fileName    文件名称
     * @param param       参数
     * @return
     */
    public static String downloadMiniCode(String accessToken, String uploadPath, String fileName, String debtorName, String param) {
        String bizPath = "files";
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        File file = new File(uploadPath + File.separator + bizPath + File.separator + nowday + File.separator + debtorName);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String savePath = uploadPath + File.separator + bizPath + File.separator + nowday + File.separator + debtorName + File.separator + fileName + ".png";
        JSONObject job = JSON.parseObject(param);
        String page = job.getString("url");
        try {
            JSONObject paramJson = new JSONObject();
            //这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("scene", job.getString("scene"));
            //注意该接口传入的是page而不是path
            paramJson.put("page", page);
            //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 430);
            paramJson.put("is_hyaline", true);
            //设置二维码颜色
            paramJson.put("auto_color", false);
            JSONObject lineColor = new JSONObject();
            lineColor.put("r", 0);
            lineColor.put("g", 0);
            lineColor.put("b", 0);
            paramJson.put("line_color", lineColor);

            String wxCodeURL = WxCode_URL.replace("ACCESS_TOKEN", accessToken);
            URL url = new URL(wxCodeURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            //httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            //httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File(savePath));
            int len;
            //设置缓冲写入
            byte[] arr = new byte[2048];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath;
    }

    public static Set<String> getUsers(String accessToken) {
        Set<String> openIds = new HashSet<>();
        return openIds;
    }

}