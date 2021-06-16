package com.ledao.common.utils.file;

import javax.print.DocFlavor;

/**
 * 媒体类型工具类
 *
 * @author lxz
 */
public class MimeTypeUtils {
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    public static final String MEDIA_MP4 = "video/mp4";

    public static final String IMAGE_TIF = "image/tif";

    public static final String IMAGE_TIFF = "image/tiff";

    public static final String IMAGE_WEBP = "image/webp";

    public static final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png", "tif", "tiff", "webp"};

    public static final String[] FLASH_EXTENSION = {"swf", "flv"};

    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb", "mp4"};

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png", "tif", "tiff", "webp",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf",
            //视频文件
            "mp4", "AVI", "mov", "rmvb", "rm", "FLV", "mp4", "3GP",
            //音频文件
            "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"
    };

    public static String getExtension(String s) {
        switch (s) {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            case MEDIA_MP4:
                return "mp4";
            case IMAGE_TIF:
                return "tif";
            case IMAGE_TIFF:
                return "tiff";
            case IMAGE_WEBP:
                return "webp";
            default:
                return "";
        }
    }
}
