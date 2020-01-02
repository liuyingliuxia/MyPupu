package me.vast.common.util;

import android.util.Log;

/**
 * 日记输出工具
 */

public class LogVast {

    private static final String TAG = "Log_";

    public static boolean DEBUG = true;

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String remark, String msg) {
        if (DEBUG) {
            Log.i(TAG, buildMessage(remark, msg));
        }
    }

    public static void d(String remark, String msg) {
        if (DEBUG) {
            Log.d(TAG, buildMessage(remark, msg));
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String remark, String msg) {
        if (DEBUG) {
            Log.e(TAG, buildMessage(remark, msg));
        }
    }

    public static void e(String msg, Throwable e) {
        if (DEBUG) {
            Log.e(TAG, msg, e);
        }
    }

    public static void e(String remark, String msg, Throwable e) {
        if (DEBUG) {
            Log.e(TAG, buildMessage(remark, msg), e);
        }
    }


    public static void v(String remark, String format) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(remark, format));
        }
    }

    public static void w(String remark, String msg) {
        if (DEBUG) {
            Log.w(TAG, buildMessage(remark, msg));
        }
    }

    public static void w(String remark, String msg, Throwable e) {
        if (DEBUG) {
            Log.w(TAG, buildMessage(remark, msg), e);
        }
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    private static String buildMessage(String remark, String msg) {
//        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
//        return new StringBuilder()
//                .append(caller.getClassName())
//                .append(".")
//                .append(caller.getMethodName())
//                .append("(): ")
//                .append(remark)
//                .append(" >>> ")
//                .append(msg).toString();

        return new StringBuilder()
                .append(remark)
                .append(" >>> ")
                .append(msg).toString();
    }
}
