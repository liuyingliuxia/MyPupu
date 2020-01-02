package me.vast.common.util;

import android.graphics.Bitmap;
import android.os.Build;


/**
 * 默认采用webp格式
 */
public class CompressFormatUtils {

    public static Bitmap.CompressFormat parseFormat(String path) {
        int dotPos = path.lastIndexOf(".");
        if (dotPos <= 0) {
            return Bitmap.CompressFormat.JPEG;
        }
        String ext = path.substring(dotPos + 1);
        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (ext.equalsIgnoreCase("png")) {
            return Bitmap.CompressFormat.PNG;
        }
        if (ext.equalsIgnoreCase("webp")) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    public static String getExt(Bitmap.CompressFormat format) {
        if (format == Bitmap.CompressFormat.WEBP) {
            return ".webp";
        }
        if (format == Bitmap.CompressFormat.PNG) {
            return ".png";
        }

        return ".jpg";
    }

    public static String getExt(String fileName) {
        return getExt(parseFormat(fileName));
    }
}
