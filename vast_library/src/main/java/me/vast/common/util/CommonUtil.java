package me.vast.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

public class CommonUtil {
    private static final long CLICK_INTERVAL = 250;
    private static long sLastClickTime = 0;

    /**
     * 控制点击频率，防止弹出多个界面
     * @return true 可以再次点击
     */
    public static boolean allowClick() {
        long curTime = System.currentTimeMillis();
        if (Math.abs(curTime - sLastClickTime) > CLICK_INTERVAL) {
            sLastClickTime = curTime;
            return true;
        }
        return false;
    }


    /**
     * 几个常用的Package命令：
     * 新浪微博（编辑界面）：com.sina.weibo //com.sina.weibo.EditActivity
     * 腾讯微博（编辑界面）：com.tencent.WBlog// com.tencent.WBlog.activity.MicroblogInput
     * 微信： com.tencent.mm //com.tencent.mm.ui.LauncherUI
     * QQ: com.tencent.mobileqq// com.tencent.mobileqq.activity.HomeActivity
     *
     * @param activity
     * @param sPackage
     * @param uiClass
     */
    public static void openLauncherPlatfrom(Activity activity, String sPackage, String uiClass) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName(sPackage, uiClass);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        activity.startActivityForResult(intent, 0);
    }


}
