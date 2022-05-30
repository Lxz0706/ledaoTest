package com.ledao.common.utils.file;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件处理工具类
 *
 * @author lxz
 */
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 默认转换后文件后缀
     */
    private static final String DEFAULT_SUFFIX = "pdf";
    /**
     * openoffice_port
     */
    private static final Integer OPENOFFICE_PORT = 8100;

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 预览pdf文件工具类
     *
     * @param response
     */
    public static void showPdf(HttpServletResponse response, String path) throws IOException {
        response.setContentType("application/pdf");
        String basePath = FileUploadUtils.getDefaultBaseDir().replace("profile", "");
        FileInputStream in = new FileInputStream(new File(basePath + path));
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[1024];
        while ((in.read(b)) != -1) {
            out.write(b);
        }
        out.flush();
        in.close();
        out.close();
    }

    public static void delFolder(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                delFolder(file1);
            }
        }
        file.delete();
    }

    ///**
    // * 方法描述 office文档转换为PDF(处理本地文件)
    // *
    // * @param sourcePath 源文件路径
    // * @param suffix     源文件后缀
    // * @return InputStream 转换后文件输入流
    // * @author tarzan
    // */
    //public static InputStream convertLocaleFile(String sourcePath, String suffix) throws Exception {
    //    File inputFile = new File(sourcePath);
    //    InputStream inputStream = new FileInputStream(inputFile);
    //    return covertCommonByStream(inputStream, suffix);
    //}
    //
    ///**
    // * 方法描述  office文档转换为PDF(处理网络文件)
    // *
    // * @param netFileUrl 网络文件路径
    // * @param suffix     文件后缀
    // * @return InputStream 转换后文件输入流
    // * @author tarzan
    // */
    //public static InputStream convertNetFile(String netFileUrl, String suffix) throws Exception {
    //    // 创建URL
    //    URL url = new URL(netFileUrl);
    //    // 试图连接并取得返回状态码
    //    URLConnection urlconn = url.openConnection();
    //    urlconn.connect();
    //    HttpURLConnection httpconn = (HttpURLConnection) urlconn;
    //    int httpResult = httpconn.getResponseCode();
    //    if (httpResult == HttpURLConnection.HTTP_OK) {
    //        InputStream inputStream = urlconn.getInputStream();
    //        return covertCommonByStream(inputStream, suffix);
    //    }
    //    return null;
    //}
    //
    ///**
    // * 方法描述  将文件以流的形式转换
    // *
    // * @param inputStream 源文件输入流
    // * @param suffix      源文件后缀
    // * @return InputStream 转换后文件输入流
    // * @author tarzan
    // */
    //public static InputStream covertCommonByStream(InputStream inputStream, String suffix) throws Exception {
    //    ByteArrayOutputStream out = new ByteArrayOutputStream();
    //    OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFICE_PORT);
    //    connection.connect();
    //    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
    //    DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
    //    DocumentFormat targetFormat = formatReg.getFormatByFileExtension(DEFAULT_SUFFIX);
    //    DocumentFormat sourceFormat = formatReg.getFormatByFileExtension(suffix);
    //    converter.convert(inputStream, sourceFormat, out, targetFormat);
    //    connection.disconnect();
    //    return outputStreamConvertInputStream(out);
    //}

    ///**
    // * 方法描述 outputStream转inputStream
    // *
    // * @author tarzan
    // */
    //public static ByteArrayInputStream outputStreamConvertInputStream(final OutputStream out) throws Exception {
    //    ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
    //    return new ByteArrayInputStream(baos.toByteArray());
    //}
}
