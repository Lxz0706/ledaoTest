package com.ledao.common.utils.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class PdfConvertUtil {

    /*
    读取pdf文字
     */
    @Test
    public void readPdfTextTest() throws IOException {
        byte[] bytes = getBytes("F:\\5f7f26fc43836.pdf");
        //加载PDF文档
        PDDocument document = PDDocument.load(bytes);
        readText(document);
    }

    /*
    pdf转换html
     */
    @Test
    public void pdfToHtmlTest() {
        String outputPath = "F:\\bk.html";
        byte[] bytes = getBytes("F:\\5f7f26fc43836.pdf");
//        try() 写在()里面会自动关闭流
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputPath)), "UTF-8"));) {
            //加载PDF文档
            PDDocument document = PDDocument.load(bytes);
            PDFDomTree pdfDomTree = new PDFDomTree();
            pdfDomTree.writeText(document, out);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


    public void pdftohtml(byte[] bytes, String htmlPath) throws Exception {
        //加载PDF文档
        PDDocument document = PDDocument.load(bytes);
        // 输出pdf文本
//        readText(document);
        //将字节流转换成字符流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), "UTF-8"));
        //实例化pdfdom树对象
        PDFDomTree pdfDomTree = new PDFDomTree();
        //开始写入html文件
        pdfDomTree.writeText(document, out);
        //在文件末尾写入要引入的js，因为我将转换的html文件放在了webapp/pdfhtml文件夹下，所以这两个js文件也要放在pdfhtml文件夹下
        out.write("<script src=\"https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js />");
        out.flush();
        out.close();
        document.close();
    }

    public void readText(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        System.out.println(text);
    }

    /*6
    将文件转换为byte数组
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 转换
     */
    public void convertText(String pdfPath) {
        PDDocument doc = null;
        OutputStream fos = null;
        Writer writer = null;
        PDFTextStripper stripper = null;
        try {
            doc = PDDocument.load(new File(pdfPath));
            fos = new FileOutputStream(pdfPath.substring(0, pdfPath.indexOf(".")) + ".doc");
            writer = new OutputStreamWriter(fos, "UTF-8");
            stripper = new PDFTextStripper();
            int pageNumber = doc.getNumberOfPages();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pageNumber);
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end..");
    }

    public static void main(String[] args) {
        PdfConvertUtil convert = new PdfConvertUtil();
        convert.convertText("F:\\5f7f26fc43836.pdf");
    }
}
