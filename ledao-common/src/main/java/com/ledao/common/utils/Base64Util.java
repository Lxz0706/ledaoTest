package com.ledao.common.utils;


import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class Base64Util {
    /**
     * 图片BASE64 编码
     */
    public static String getPicBASE64(String picPath) {
        String content = null;
        try {
            FileInputStream fileForInput = new FileInputStream(picPath);
            byte[] bytes = new byte[fileForInput.available()];
            fileForInput.read(bytes);
            content = Arrays.toString(Base64.getEncoder().encode(bytes)); // 具体的编码方法
            fileForInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 对图片BASE64 解码
     */
    public static void getPicFormatBASE64(String str, String picPath) {
        try {
            byte[] result = Base64.getDecoder().decode(str.trim());
            File file = new File(picPath);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                System.out.println("//不存在");
                file.mkdir();
            } else {
                System.out.println("//目录存在");
            }
            RandomAccessFile inOut = new RandomAccessFile(picPath, "rw"); // r,rw,rws,rwd
            //用FileOutputStream亦可
            inOut.write(result);
            inOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File GenerateImage(String imgStr, String fileDir, String fileName) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            //图像数据为空
            return null;
        }

        //图片的输出流
        OutputStream imageOut = null;
        try {
            //Base64解码
            byte[] b = Base64.getDecoder().decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            //先判断目录是否存在
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //如果文件不存在，则保存。如果存在则跳过？
            String realFilePath = fileDir + File.separator + fileName;
            File file = new File(realFilePath);
            if (!file.exists()) {
                //1.创建文件，作为图片的外壳；
                file.createNewFile();
                imageOut = new FileOutputStream(file);
                imageOut.write(b);
                imageOut.flush();
                imageOut.close();
                return file;
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
