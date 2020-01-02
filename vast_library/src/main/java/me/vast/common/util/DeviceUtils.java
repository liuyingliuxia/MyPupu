package me.vast.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;

import me.vast.common.widgets.systembar.SystemBarTintManager;

/**
 * Created by zhengz on 2017/8/30.
 */

public class DeviceUtils {
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";

    public static int getStatusBarHeight(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                String key;
                if (isPortrait(context.getResources())) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return getInternalDimensionSize(res, key);
            }
        }
        return result;
    }

    private static boolean isPortrait(Resources res) {
        return (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }


    @TargetApi(14)
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(SystemBarTintManager.sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(SystemBarTintManager.sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //    /**
    //     * 获取应用签名
    //     *
    //     * @param context
    //     * @return
    //     */
    //    public static String getSign(Context context) {
    //        PackageManager pm = context.getPackageManager();
    //        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
    //        Iterator<PackageInfo> iter = apps.iterator();
    //        while (iter.hasNext()) {
    //            PackageInfo info = iter.next();
    //            String packageName = info.packageName;
    //            // 按包名 取签名
    //            if (packageName.equals("com.goood.casedesign")) {
    //                return MD5.getMD5Str32(info.signatures[0].toByteArray());
    //            }
    //        }
    //        return null;
    //    }

//    /**
//     * 获得软件版号versionCode
//     * Fixme 使用BuildConfig.VERSION_CODE 替代
//     *
//     * @param context
//     * @return
//     */
//    public static int getVersionCode(Context context) {
//        try {
//            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            return packInfo.versionCode;
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    /**
//     * 获得软件版号versionName
//     * Fixme 使用BuildConfig.VERSION_NAME 替代
//     *
//     * @param context
//     * @return
//     */
//    public static String getVersionName(Context context) {
//        try {
//            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            return packInfo.versionName;
//        } catch (Exception e) {
//            return "";
//        }
//    }

    /**
     * 获取application下的metaData值
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getApplocationMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                Object apiKey = metaData.get(metaKey);
                if (null != apiKey) {
                    return apiKey.toString();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取activity 中的的metaData值
     *
     * @param activity
     * @param metaKey
     * @return
     */
    public static String getActivityMetaValue(Activity activity, String metaKey) {
        Bundle metaData = null;
        if (activity == null || metaKey == null) {
            return null;
        }
        try {
            ActivityInfo ai = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                Object apiKey = metaData.get(metaKey);
                if (null != apiKey) {
                    return apiKey.toString();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 最简单的判断第三方应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isHavaInstallPackage(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


    // 安装程序

    /**
     * 安装程序
     *
     * @param context
     * @param path
     */
    public static void installApk(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

//    /**
//     * 判断APP是否在前台   zhengz备注过时了
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isAppForeground(Context context) {
//        if (context == null) {
//            return false;
//        }
//        return context.getPackageName().equals(getProcess(context));
//    }
//
//
//    public static String getProcess(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            return getProcessNew(context);
//        } else {
//            return getProcessOld(context);
//        }
//    }
//
//    //API 21 and above
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @SuppressWarnings("ResourceType")
//    private static String getProcessNew(Context context) {
//        String topPackageName = null;
//        try {
//            // 使用情况统计(.xml须加权限)
//            UsageStatsManager usage = (UsageStatsManager) context.getSystemService("usagestats");
//            long time = System.currentTimeMillis();
//            // 获取10秒内的历史记录
//            List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
//            if (stats != null) {
//                SortedMap<Long, UsageStats> runningTask = new TreeMap<>();
//                for (UsageStats usageStats : stats) {
//                    runningTask.put(usageStats.getLastTimeUsed(), usageStats);
//                }
//                if (runningTask.isEmpty()) {
//                    return null;
//                }
//                topPackageName = runningTask.get(runningTask.lastKey()).getPackageName();
//            }
//        } catch (Exception e) {
//
//        }
//        return topPackageName;
//    }
//
//    /**
//     * @deprecated As of {@link Build.VERSION_CODES#LOLLIPOP}
//     */
//    @SuppressWarnings("deprecation")
//    private static String getProcessOld(Context context) {
//        String topPackageName = null;
//        try {
//            ActivityManager activity = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> runningTask = activity.getRunningTasks(1);
//            if (runningTask != null && runningTask.size() > 0) {
//                topPackageName = runningTask.get(0).topActivity.getPackageName();
//            }
//        } catch (Exception e) {
//
//        }
//        return topPackageName;
//    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

//    public static String getDeviceInfo(Context context) {
//        try {
//            org.json.JSONObject json = new org.json.JSONObject();
//            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            String device_id = null;
//            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
//                device_id = tm.getDeviceId();
//            }
//            String mac = null;
//            FileReader fstream = null;
//            try {
//                fstream = new FileReader("/sys/class/net/wlan0/address");
//            } catch (FileNotFoundException e) {
//                try {
//                    fstream = new FileReader("/sys/class/net/eth0/address");
//                } catch (FileNotFoundException ex) {
//
//                }
//            }
//            BufferedReader in = null;
//            if (fstream != null) {
//                try {
//                    in = new BufferedReader(fstream, 1024);
//                    mac = in.readLine();
//                } catch (IOException e) {
//
//                } finally {
//                    if (fstream != null) {
//                        try {
//                            fstream.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (in != null) {
//                        try {
//                            in.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//            json.put("mac", mac);
//            if (TextUtils.isEmpty(device_id)) {
//                device_id = mac;
//            }
//            if (TextUtils.isEmpty(device_id)) {
//                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            }
//            json.put("device_id", device_id);
//            return json.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    /**
     * 添加当前应用的桌面快捷方式
     *
     * @param cx
     */

    /**
     * @param cx
     * @param title
     * @param resourceId 图标
     */
    public static void createShortcut(Context cx, String title, int resourceId) {
        final PackageManager pm = cx.getPackageManager();
        final String packageName = cx.getPackageName();
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Intent shortcutIntent = pm.getLaunchIntentForPackage(packageName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 获取当前应用名称
//        String title = "";
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
//        if (TextUtils.isEmpty(title)) {
//            title = cx.getString(R.string.app_name);
//        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 不允许重复创建（不一定有效）
        shortcut.putExtra("duplicate", false);
        // 快捷方式的图标
        Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource.fromContext(cx, resourceId);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        cx.sendBroadcast(shortcut);
    }


    /**
     * 判断是否存在快捷方式
     *
     * @param cx
     * @return
     */
    public static boolean hasShortcut(Context cx, String title) {
        final PackageManager pm = cx.getPackageManager();
        final String packageName = cx.getPackageName();
        // 获取当前应用名称
//        String title = null;
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
//        if (null == title) {
//            title = cx.getString(R.string.app_name);
//        }
        String url;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        Cursor cursor = null;
        try {
            ContentResolver resolver = cx.getContentResolver();
            cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{title}, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        } catch (Exception e) {

        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 删除当前应用的桌面快捷方式
     *
     * @param cx
     */
    public static void delShortcut(Context cx, String title) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 获取当前应用名称
//        String title = null;
        final PackageManager pm = cx.getPackageManager();
        final String packageName = cx.getPackageName();
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
//        if (null == title) {
//            title = cx.getString(R.string.app_name);
//        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        Intent shortcutIntent = pm.getLaunchIntentForPackage(packageName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        cx.sendBroadcast(shortcut);
    }


    public static void copyClipboard(Context context, String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


//    /**
//     * 获取当前手机系统语言。
//     *
//     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
//     */
//    public static String getSystemLanguage() {
//        return Locale.getDefault().getLanguage();
//    }
//
//    /**
//     * 获取当前系统上的语言列表(Locale列表)
//     *
//     * @return  语言列表
//     */
//    public static Locale[] getSystemLanguageList() {
//        return Locale.getAvailableLocales();
//    }

//    /**
//     * 获取当前手机系统版本号
//     *
//     * @return  系统版本号
//     */
//    public static String getSystemVersion() {
//        return android.os.Build.VERSION.RELEASE;
//    }

//    /**
//     * 获取手机型号
//     *
//     * @return  手机型号
//     */
//    public static String getSystemModel() {
//        return android.os.Build.MODEL;
//    }
//
//    /**
//     * 获取手机厂商
//     *
//     * @return  手机厂商
//     */
//    public static String getDeviceBrand() {
//        return android.os.Build.BRAND;
//    }
//
    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        try {
            if (tm != null) {
                return tm.getDeviceId();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static String getSerial() {
        String serial;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception e) {
            serial = "";
        }
        return serial;
    }


    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideInputMethodManager(Context context, View view) {
        if (null == view || null == context) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public static void showInputMethodManager(Context context, View view) {
        if (null == view || null == context) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }


    /**
     * 获取设备唯一识别码
     * http://blog.csdn.net/sunsteam/article/details/73189268
     *
     * @param context
     */
    public static String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String imei = getIMEI(context);
        String serial = getSerial();

        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(androidID)) {
            builder.append("01").append(androidID);
        }
        if (!TextUtils.isEmpty(imei)) {
            builder.append("02").append(imei);
        }
        if (!TextUtils.isEmpty(serial)) {
            builder.append("03").append(serial);
        }
        return DigestUtils.MD5.gen(builder.toString());
    }

}
