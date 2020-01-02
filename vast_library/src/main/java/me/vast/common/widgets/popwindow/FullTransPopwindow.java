package me.vast.common.widgets.popwindow;

/**
 * author : zhengz
 * time   : 2018/3/19
 * desc   :
 */

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * 全透明的PopupWindow
 */

public abstract class FullTransPopwindow extends PopupWindow {

    public FullTransPopwindow(View contentView) {
        super(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable());
        contentView.setFocusableInTouchMode(true);
    }

}
