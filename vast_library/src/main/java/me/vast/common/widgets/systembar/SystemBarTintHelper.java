package me.vast.common.widgets.systembar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


public class SystemBarTintHelper {

    private SystemBarTintManager mTintManager;

    public SystemBarTintHelper(Activity activity) {
        initStatusBarTint(activity);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void initStatusBarTint(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatus(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, activity);
        }
        mTintManager = new SystemBarTintManager(activity);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
    }

    public void setStatusBarTintColor(int color) {
        if (null != mTintManager) {
            mTintManager.setStatusBarTintColor(color);
        }
    }

    public void setStatusBarTintResource(int res) {
        if (null != mTintManager) {
            mTintManager.setStatusBarTintResource(res);
        }
    }

    public void setStatusBarTintEnabled(boolean enabled) {
        if (null != mTintManager) {
            mTintManager.setStatusBarTintEnabled(enabled);
        }
    }

    public void setStatusBarScrimOpacity(float scrimOpacity) {
        if (null != mTintManager) {
            mTintManager.setStatusBarScrimOpacity(scrimOpacity);
        }
    }

    public int getStatusBarHeight() {
        if (null != mTintManager) {
            return mTintManager.getConfig().getStatusBarHeight();
        }

        return 0;
    }

//    public void setupView(Activity activity, View view, FrameLayout.LayoutParams params) {
//        if (null != mTintManager) {
//            mTintManager.setupView(activity, view, params);
//        }
//    }

}
