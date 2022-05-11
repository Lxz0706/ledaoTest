package com.ledao.common.utils;

import com.ledao.common.utils.file.FileUploadUtils;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * @author lxz
 * @date 2021/12/23
 */
public class WxImageTool {


    public void generateSharePoster(String savePath, String qrcodeImg, String storeName) throws Exception {
        Color grey = new Color(155, 155, 155); // 灰色
        BufferedImage qrcodeBuffer = ImageIO.read(new URL(qrcodeImg));
        // 小程序码缩放
        BufferedImage qrcodeMinBuffer = WxImageTool.resizeByWidth(qrcodeBuffer, 170);
        BufferedImage imgWithTips = WxImageTool.addTxtAtXy(qrcodeBuffer, storeName, 160, 150, new Font("宋体", Font.PLAIN, 25), grey);
        BufferedImage imgWithGoods = WxImageTool.synthesisPicAtXy(imgWithTips, qrcodeMinBuffer, 35, 205);
        // 保存图片
        ImageIO.write(imgWithGoods, "png", new File(savePath));
    }


    /**
     * 获取指定字体指定内容的宽度
     *
     * @param font    字体
     * @param content 内容
     * @return
     */
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    /**
     * 获取指定字体指定内容的宽度
     *
     * @param font    字体
     * @param content 内容
     * @return
     */
    public static int getWordWidthBody(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.stringWidth(content);
    }


    /**
     * 按比例裁剪图片
     *
     * @param image  待处理的图片流
     * @param startX 开始x坐标
     * @param startY 开始y坐标
     * @param endX   结束x坐标
     * @param endY   结束y坐标
     * @return
     */
    public static BufferedImage crop(BufferedImage image, int startX, int startY, int endX, int endY) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (startX <= -1) {
            startX = 0;
        }
        if (startY <= -1) {
            startY = 0;
        }
        if (endX <= -1) {
            endX = width - 1;
        }
        if (endY <= -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX, endY, image.getType());
        for (int y = startY; y < endY + startY; y++) {
            for (int x = startX; x < endX + startX; x++) {
                int rgb = image.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }


    /**
     * 图片缩放
     *
     * @param originalImage 原始图片
     * @param width         宽度
     * @param height        高度
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        /* 新建一个空白画布 */
        BufferedImage image = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = image.createGraphics();
        /* 设置背景透明 */
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d = image.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return image;
    }

    /**
     * 实现图像的等比缩放(固定宽度)
     *
     * @param source  待处理的图片流
     * @param targetW 宽度
     * @return
     */
    public static BufferedImage resizeByWidth(BufferedImage source, double targetW) {
        double targetH = 0;
        double width = source.getWidth();// 图片宽度
        double height = source.getHeight();// 图片高度
        targetH = targetW / width * height;

        return zoomInImage(source, (int) targetW, (int) targetH);

    }

    /**
     * 实现图像的等比缩放(固定高度)
     *
     * @param source  待处理的图片流
     * @param targetH 高度
     * @return
     */
    public static BufferedImage resizeByHeight(BufferedImage source, double targetH) {
        double targetW;
        double width = source.getWidth();// 图片宽度
        double height = source.getHeight();// 图片高度
        targetW = targetH / height * width;
        return zoomInImage(source, (int) targetW, (int) targetH);
    }

    /***
     * 将图片处理为圆角图片
     * @param srcImgPath 源图片文件路径
     * @param destImgPath  新生成的图片的保存路径，需要带有保存的文件名和后缀
     * @param targetSize 文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @return 文件的保存路径
     */
    public static String roundImage(String srcImgPath, String destImgPath, int targetSize, int cornerRadius) {
        BufferedImage bufferedImage = null;
        BufferedImage circularBufferImage = null;
        try {
            bufferedImage = ImageIO.read(new File(srcImgPath));
            circularBufferImage = roundImage(bufferedImage, targetSize, cornerRadius);
            ImageIO.write(circularBufferImage, "png", new File(destImgPath));
            return destImgPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedImage != null) {
                bufferedImage.flush();
            }
            if (circularBufferImage != null) {
                circularBufferImage.flush();
            }
        }
        return destImgPath;
    }

    /**
     * 将图片处理为圆角图片
     *
     * @param image        待处理图片
     * @param targetSize   直径
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @return
     */
    public static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    /**
     * 将第二张图片放到第一张的指定位置
     *
     * @param imageFirst
     * @param imageSecond
     * @param x
     * @param y
     */
    public static BufferedImage synthesisPicAtXy(BufferedImage imageFirst, BufferedImage imageSecond, int x, int y) {
        BufferedImage image = null;
        try {
            /* 读取第一张图片 宽高 */
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度

            /* 读取第二张图片 宽高 */
            int widthTwo = imageSecond.getWidth();// 图片宽度
            int heightTwo = imageSecond.getHeight();// 图片高度

            /* 计算总宽度 */
            int w = 0;
            if (x + widthTwo > width) {
                w = widthTwo + x;
            } else {
                w = width;
            }

            /* 计算总高度 */
            int h = 0;
            if (y + heightTwo > height) {
                h = heightTwo + y;
            } else {
                h = height;
            }

            /* 新建一个空白画布 */
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            /* 设置背景透明 */
            image = g2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
            g2d = image.createGraphics();
            g2d.drawImage(imageFirst, 0, 0, w, h, null);
            g2d.drawImage(imageSecond, x, y, widthTwo, heightTwo, null);

            return image;

        } catch (Exception e) {
            if (image != null) {
                image.flush();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将第二张图片放到第一张的指定位置
     *
     * @param first
     * @param second
     * @param out
     * @param x
     * @param y
     */
    public static String synthesisPicAtXy(String first, String second, String out, int x, int y) {
        BufferedImage imageFirst = null;
        BufferedImage imageSecond = null;
        BufferedImage outBuffered = null;
        try {
            File fileOne = new File(first);
            imageFirst = ImageIO.read(fileOne);
            File fileTwo = new File(second);
            imageSecond = ImageIO.read(fileTwo);
            outBuffered = synthesisPicAtXy(imageFirst, imageSecond, x, y);
            File outFile = new File(out);
            assert outBuffered != null;
            ImageIO.write(outBuffered, "png", outFile);// 写图片
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageFirst != null) {
                imageFirst.flush();
            }
            if (imageSecond != null) {
                imageSecond.flush();
            }
            if (outBuffered != null) {
                outBuffered.flush();
            }
        }
        return null;
    }

    /**
     * 将文字添加到图片指定的位置
     *
     * @param src
     * @param out
     * @param x
     * @param y
     * @param center 可选居中 默认false
     * @return
     */
    public static String addTxtAtXy(String src, String out, String txt, int x, int y, boolean center, Font font, Color color) {
        BufferedImage picBuffer = null;
        BufferedImage outBuffered;
        try {
            File fileOne = new File(src);
            picBuffer = ImageIO.read(fileOne);
            outBuffered = addTxtAtXy(picBuffer, txt, x, y, font, color);
            File outFile = new File(out);
            assert outBuffered != null;
            ImageIO.write(outBuffered, "png", outFile);// 写图片
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (picBuffer != null) {
                picBuffer.flush();
            }
        }
        return null;
    }

    /**
     * 将文字txt添加到图片指定的位置(x,y)
     *
     * @param src
     * @param txt
     * @param x
     * @param y
     * @return
     */
    public static BufferedImage addTxtAtXy(BufferedImage src, String txt, int x, int y, Font font, Color color) {
        BufferedImage outBuffer;
        try {

            /* 读取图片 宽高 */
            int width = src.getWidth();// 图片宽度
            int height = src.getHeight();// 图片高度

            /* 新建一个空白画布 */
            outBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outBuffer.createGraphics();
            /* 设置背景透明 */
            outBuffer = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

            g2d = outBuffer.createGraphics();
            g2d.drawImage(src, 0, 0, width, height, null);

            g2d.setColor(color);
            g2d.setFont(font);

            // 10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g2d.drawString(txt, x, y);
            g2d.dispose();
            return outBuffer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String generateCode(String filePath, String outPath, String text, Integer width, Integer height) {
        // 添加字体的属性设置
        Font font = new Font("宋体", Font.BOLD, 27);
        String imgName = outPath;
        try {
            // 加载本地背景图片
            String imageLocalUrl = FileUploadUtils.getDefaultBaseDir() + File.separator + "back.png";
            BufferedImage imageLocal = ImageIO.read(new FileInputStream(imageLocalUrl));
            // 加载二维码
            BufferedImage imageCode = ImageIO.read(new FileInputStream(filePath));
            if (new File(filePath).exists()) {
                new File(filePath).delete();
            }
            // 以本地图片为模板
            Graphics2D g = imageLocal.createGraphics();
            // 在模板上添加二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
            g.drawImage(imageCode, 100, 30, width, height, null);
            // 设置文本样式
            g.setFont(font);
            g.setColor(Color.BLACK);
            int fontHeight = (int) font.getSize2D();
            for (String string : text.split(";")) {
                // 得到当前的font metrics
                FontMetrics metrics = g.getFontMetrics();
                int StrPixelWidth = metrics.stringWidth(string); // 字符串长度（像素） str要打印的字符串
                int lineSize = (int) Math.ceil(StrPixelWidth * 1.0 / width);// 算出行数
                if (width < StrPixelWidth) {
                    // 页面宽度（width）小于 字符串长度
                    StringBuilder sb = new StringBuilder();
                    // 存储每一行的字符串
                    int j = 0;
                    int tempStart = 0;
                    String tempStrs[] = new String[lineSize];
                    // 存储换行之后每一行的字符串
                    for (int i1 = 0; i1 < string.length(); i1++) {
                        char ch = string.charAt(i1);
                        sb.append(ch);
                        Rectangle2D bounds2 = metrics.getStringBounds(sb.toString(), null);
                        int tempStrPi1exlWi1dth = (int) bounds2.getWidth();
                        if (tempStrPi1exlWi1dth > width) {
                            tempStrs[j++] = string.substring(tempStart, i1);
                            tempStart = i1;
                            sb.delete(0, sb.length());
                            sb.append(ch);
                        }

                        // 最后一行
                        if (i1 == string.length() - 1) {
                            tempStrs[j] = string.substring(tempStart);
                        }
                    }
                    for (int p = 0; p < tempStrs.length; p++) {
                        if(StringUtils.isNotEmpty(tempStrs[p])){
                            g.drawString(tempStrs[p], 410, (fontHeight + 25) * (p + 1));
                        }
                    }
                    fontHeight = (fontHeight + 25) * tempStrs.length;
                } else {
                    fontHeight = fontHeight + 50;
                    g.drawString(string, 410, fontHeight);
                }
            }
            // 获取新文件的地址
            File outputfile = new File(imgName);
            // 生成新的合成过的用户二维码并写入新图片
            ImageIO.write(imageLocal, "png", outputfile);
            // 完成模板修改
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回给页面的图片地址(因为绝对路径无法访问)
        imgName = outPath;
        return imgName;
    }

    public static void textToImage(String filePath, String outPath, String text, Integer width, Integer height) {
        try {
            Font font = new Font("宋体", Font.BOLD, 28);
            String imgName = "D:\\back.png";
            String imageLocalUrl = "D:\\profile\\back.png";
            BufferedImage imageLocal = ImageIO.read(new FileInputStream(imageLocalUrl));
            // 以本地图片为模板
            Graphics2D g = imageLocal.createGraphics();
            // 在模板上添加二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
            // 设置文本样式
            g.setFont(font);
            g.setColor(Color.BLACK);
            int fontHeight = (int) font.getSize2D();
            for (String string : text.split(";")) {
                System.out.println(string);
// 得到当前的font metrics
                FontMetrics metrics = g.getFontMetrics();
                int StrPixelWidth = metrics.stringWidth(string); // 字符串长度（像素） str要打印的字符串
                int lineSize = (int) Math.ceil(StrPixelWidth * 1.0 / width) + 1;// 算出行数
                if (width < StrPixelWidth) {
                    // 页面宽度（width）小于 字符串长度
                    StringBuilder sb = new StringBuilder();
                    // 存储每一行的字符串
                    int j = 0;
                    int tempStart = 0;
                    String tempStrs[] = new String[lineSize];
                    // 存储换行之后每一行的字符串
                    for (int i1 = 0; i1 < string.length(); i1++) {
                        char ch = string.charAt(i1);
                        sb.append(ch);
                        Rectangle2D bounds2 = metrics.getStringBounds(sb.toString(), null);
                        int tempStrPi1exlWi1dth = (int) bounds2.getWidth();
                        if (tempStrPi1exlWi1dth > width) {
                            tempStrs[j++] = string.substring(tempStart, i1);
                            tempStart = i1;
                            sb.delete(0, sb.length());
                            sb.append(ch);
                        }
                        // 最后一行
                        if (i1 == string.length() - 1) {
                            tempStrs[j] = string.substring(tempStart);
                        }
                    }
                    for (int p = 0; p < tempStrs.length; p++) {
                        g.drawString(tempStrs[p], 150, (fontHeight + 5) * (p + 1));
                    }
                    fontHeight = (fontHeight + 15) * tempStrs.length;
                } else {
                    fontHeight = fontHeight + 25;
                    g.drawString(string, 150, fontHeight);
                }
            }


            // 获取新文件的地址
            File outputfile = new File(imgName);
            // 生成新的合成过的用户二维码并写入新图片
            ImageIO.write(imageLocal, "png", outputfile);
            // 完成模板修改
            g.dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        String str = "江苏青泓信息科技、无锡檀泉科技有限公司;1份;11页;原件";
        textToImage("D:\\back.png", "D:\\profile\\back.png", str, 290, 140);
    }
}
