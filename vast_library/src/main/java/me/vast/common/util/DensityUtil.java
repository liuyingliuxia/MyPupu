package me.vast.common.util;

import android.content.Context;

/**
 * zhengz 2017.12.6
 * 重新整理
 */
public class DensityUtil {

    /**
     * dp 转 px
     *
     * @param context
     * @param dpValue
     * @return 浮点型
     */
    public static float dip2px(Context context, float dpValue) {
        return dpValue * context.getResources().getDisplayMetrics().density;
    }

    /**
     * px 转 dp
     *
     * @param context
     * @param pxValue
     * @return 浮点型
     */
    public static float px2dip(Context context, float pxValue) {
        return pxValue / context.getResources().getDisplayMetrics().density;
    }


    /**
     * sp 转 px
     *
     * @param context
     * @param spValue
     * @return 浮点型
     */
    public static float sp2px(Context context, float spValue) {
        return spValue * context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * px 转 sp
     *
     * @param context
     * @param pxValue
     * @return 浮点型
     */
    public static float px2sp(Context context, float pxValue) {
        return pxValue / context.getResources().getDisplayMetrics().scaledDensity;
    }


    /**
     * dp 转 px
     *
     * @param context
     * @param dpValue
     * @return int整型
     */
    public static int dip2pxInt(Context context, float dpValue) {
        return (int) (dip2px(context, dpValue) + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context
     * @param pxValue
     * @return int整型
     */
    public static int px2dipInt(Context context, float pxValue) {
        return (int) (px2dip(context, pxValue) + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context
     * @param spValue
     * @return int整型
     */
    public static int sp2pxInt(Context context, float spValue) {
        return (int) (sp2px(context, spValue) + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context
     * @param pxValue
     * @return int整型
     */
    public static int px2spInt(Context context, float pxValue) {
        return (int) (px2sp(context, pxValue) + 0.5f);
    }


}
