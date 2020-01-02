package me.vast.common.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.vast.common.R;


/**
 * Created by zhengz on 2016/3/25.
 * Toast 或 Snackbar
 */
public class ToastUtils {
    private static Toast toast;

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, @StringRes int res) {
        show(context, context.getString(res), Toast.LENGTH_SHORT);
    }

    /**
     * @param context    上下文
     * @param text       文本
     * @param duration   @Toast.Duration
     */
    public static void show(Context context, CharSequence text, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }


    public static void snackbar(View view, @StringRes int resId) {
        if (resId != 0) {
            snackbar(view, view.getResources().getText(resId));
        }
    }

    public static void snackbar(View view, @StringRes int resId, int duration) {
        if (resId != 0) {
            snackbar(view, view.getResources().getText(resId), duration);
        }
    }


    public static void snackbar(View view, CharSequence text) {
        snackbar(view, text, Snackbar.LENGTH_SHORT);
    }

    public static void snackbar(View view, CharSequence text, int duration) {
        if (!TextUtils.isEmpty(text)) {
            Snackbar snackbar = Snackbar.make(view, text, duration);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

}
