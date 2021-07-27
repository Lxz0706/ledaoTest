package com.ledao.common.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName WorldUtil
 * @Description TODO
 * @Author 87852
 * @Date 2021/7/19 13:31
 * @Version 1.0
 */
public class WorldUtil {
    /**
     * 导出world
     *
     * @param dataMap      数据集
     * @param templateName 模板名称
     * @param filePath     模板路径
     * @param fileName     文件名
     * @param response
     */
    public static void exportDoc(Map dataMap, String templateName, String filePath, String fileName, HttpServletResponse response) {
        try {
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            //设置模板所在文件夹
            configuration.setClassForTemplateLoading(WorldUtil.class, filePath);
            //获取模板
            Template template = configuration.getTemplate(templateName);
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName + ".doc", "UTF-8"));
            // 定义输出类型
            response.setContentType("application/msword");
            template.process(dataMap, new OutputStreamWriter(response.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成word文件并返回二进制
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：model.ftl
     * @param fileName 生成的文件名称，例如：Test.doc
     * @param basePackagePath  模板存放位置
     * @return
     * @throws IOException
     */
    public static byte[] createWordByte(Map<String, Object> dataMap, String templateName, String fileName, String basePackagePath) throws IOException {
        File outFile = null;
        Writer out = null;
        try {
            //创建配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //设置编码
            configuration.setDefaultEncoding("utf-8");
            //ftl模板文件
            configuration.setClassForTemplateLoading(WorldUtil.class, basePackagePath);
            //获取模板
            Template template = configuration.getTemplate(templateName);
            //输出文件
            outFile = new File(File.separator + fileName);
            //将模板和数据模型合并生成文件
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            //生成文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            assert out != null;
            out.flush();
            out.close();
        }
        return getWordByte(outFile);
    }

    /**
     * 根据文件生成二进制数组
     *
     * @param outFile
     * @return
     */
    private static byte[] getWordByte(File outFile) {
        byte[] wordByte = null;
        try {
            FileInputStream fis = new FileInputStream(outFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            wordByte = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordByte;
    }

    /**
     * 生成word文件到本地
     *
     * @param dataMap      word中需要展示的动态数据，用map集合来保存
     * @param templatePath word模板的路径
     * @param templateName word模板名称，例如：model.ftl
     * @param filePath     文件生成的目标路径，例如：D:\\freemarker
     * @param fileName     生成的文件名称，例如：Test.doc
     */
    public static void createWordToLocal(Map<String, Object> dataMap, String templatePath, String templateName, String filePath, String fileName) throws IOException {
        File outFile = null;
        Writer out = null;
        try {
            //创建配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //设置编码
            configuration.setDefaultEncoding("utf-8");
            //ftl模板文件
            configuration.setClassForTemplateLoading(WorldUtil.class, templatePath);
            //获取模板
            Template template = configuration.getTemplate(templateName);
            //输出文件
            outFile = new File(filePath + File.separator + fileName);
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                if (!outFile.getParentFile().mkdirs()) {
                    //log.error("创建文件到本地失败 dataMap：{}", JSON.toJSONString(dataMap));
                }
            }
            //将模板和数据模型合并生成文件
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            //生成文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            out.flush();
            out.close();
        }
    }
}
