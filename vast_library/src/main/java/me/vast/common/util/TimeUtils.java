package me.vast.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : zhengz
 * time   : 2018/3/15
 * desc   : 时间辅助类
 */

public class TimeUtils {

    public static String timeFormat(long timeMillis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(timeMillis));
    }


    public static String format0(long timestamp) {
        return timeFormat(timestamp, "yyyy-MM-dd HH:mm");
    }

    public static String format1(long timestamp) {
        return timeFormat(timestamp, "yyyy-MM-dd");
    }

    public static String format2(long timestamp) {
        return timeFormat(timestamp, "MM-dd");
    }

}
