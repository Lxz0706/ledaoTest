package com.ledao.common.utils.pdf;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName FileToPdf
 * @Description TODO
 * @Author 87852
 * @Date 2021/5/13 16:22
 * @Version 1.0
 */
public class FileToPdf {
    @Autowired
    private DocumentConverter converter; // 转换器

    @GetMapping("/officeToPdf/url")
    public void officeToPdf(String url, HttpServletResponse response) throws IOException, OfficeException {
        //获取当前地址下的文件
        File file = new File("c:/" + url);
        String oldSuffix = url.substring(url.lastIndexOf(".") + 1);
        //默认转pdf,excel转html
        String suffix = ".pdf";
        if ("xlsx".equals(oldSuffix) || "xls".equals(oldSuffix)) {
            suffix = ".html";
        }

        //转换的文件存放位置
        String newUrl = url.replace("." + oldSuffix, suffix);

        File newFile = new File("c:/" + url);

        converter.convert(file).to(newFile).execute();
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream in = new FileInputStream(newFile);// 读取文件

        in.close();
        outputStream.close();
        newFile.delete();
    }
}
