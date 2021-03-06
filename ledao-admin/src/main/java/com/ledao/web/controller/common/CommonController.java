package com.ledao.web.controller.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ledao.common.config.Global;
import com.ledao.common.config.ServerConfig;
import com.ledao.common.constant.Constants;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 通用请求处理
 *
 * @author lxz
 */
@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private DocumentConverter converter;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, true);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    /**
     * @param filePath 文件将要保存的目录
     * @param method   请求方法，包括POST和GET
     * @param url      请求的路径
     * @return
     * @功能 下载临时素材接口
     */

    public static File saveUrlAs(String url, String filePath, String method) {
        //创建不同的文件夹目录
        File file = new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath + "123.png");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
        return file;
    }

    @GetMapping("/common/download/getFileByUrl")
    public void getImage(String filePath, HttpServletRequest request, HttpServletResponse response) {
        // 从服务器端获得文件流，并输出到页面
        InputStream inputStream = getInputStream(filePath);
        writeFile(response, inputStream);
    }


    /**
     * @return
     * @description: 从服务器获得一个输入流
     */
    public static InputStream getInputStream(String urlPath) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * @param resp
     * @param inputStream
     * @description: 将输入流输出到页面
     */
    public static void writeFile(HttpServletResponse resp, InputStream inputStream) {
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/common/officeToPdf")
    public void officeToPdf(String url, HttpServletResponse response) throws FileNotFoundException {
        String newUrl = "";
        response.setCharacterEncoding("UTF-8");
        byte[] b = new byte[1024];
        try {
            //获取当前地址下的文件
            File file = new File(Global.getProfile().substring(0,Global.getProfile().indexOf("/")) + url);
            if (!file.exists()) {
                throw new RuntimeException("源文件不存在");
            }
            String oldSuffix = url.substring(url.lastIndexOf(".") + 1);
            //默认转pdf,excel转html
            String suffix = ".pdf";
            if ("txt".equals(oldSuffix)) {
                charsetEnc(Global.getProfile().substring(0,Global.getProfile().indexOf("/")) + url, "UTF-8");
            }
            if ("xlsx".equals(oldSuffix) || "xls".equals(oldSuffix) || "txt".equals(oldSuffix)) {
                suffix = ".html";
            }

            //转换的文件存放位置
            newUrl = url.replace("." + oldSuffix, suffix);
            File newFile = new File(Global.getProfile().substring(0,Global.getProfile().indexOf("/")) + newUrl);

            converter.convert(file).to(newFile).execute();
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(newFile);
            int len = 0;
            while ((len = in.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }

            outputStream.flush();

            in.close();
            outputStream.close();
            newFile.delete();
        } catch (OfficeException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String ReaderEcd;//读取文件的字符编码设置

    private static String outEcd;//写入文件的字符编码设置

    /**
     * 功能：更改单文件字符编码
     *
     * @param fileUrl 文件
     * @throws IOException
     */
    public static void charsetEnc(String fileUrl, String code) throws Exception {
        String context = "";
        String cs = codeString(fileUrl);
        InputStreamReader is = new InputStreamReader(new FileInputStream(fileUrl), cs);
        BufferedReader bdf = new BufferedReader(is);
        String str = null;
        while ((str = bdf.readLine()) != null) {
            context += str + "\n";
        }
        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(fileUrl), code);
        BufferedWriter bdw = new BufferedWriter(os);
        bdw.write(context);
        bdw.close();
        os.close();
        bdf.close();
        is.close();
    }

    /**
     * 获得文件编码
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII";
                break;
            default:
                code = "GBK";
        }

        return code;
    }

    /**
     *      * 将一个文档自动转换为另一个文档格式
     *      * @param souseFileName  源文件路径
     *      * @param desFileName    目标文件路径
     *      * @param code           目标文件的文档存放格式
     *      * @throws IOException
     *     
     */
    public static void fileConvertToCode(String souseFileName, String desFileName, String code) throws Exception {
        String cs = codeString(souseFileName);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(souseFileName), cs);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(desFileName), code);
        int len = -1;
        char[] cbuf = new char[1024];
        while ((len = isr.read(cbuf, 0, cbuf.length)) != -1) {
            osw.write(cbuf, 0, len);
        }
        osw.flush();
        osw.close();
        isr.close();
    }

}
