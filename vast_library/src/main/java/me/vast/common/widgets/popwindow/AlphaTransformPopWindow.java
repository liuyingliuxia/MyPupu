package me.vast.common.widgets.popwindow;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopupWindow 动画背景透明度
 */

public abstract class AlphaTransformPopWindow extends PopupWindow {
    private ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1, 0).setDuration(500);
    /**
     * 最大透明度
     */
    private final float MAX_ALPHA_VALUE = 0.5f;

    /**
     * 是否背景显示透明
     */
    public boolean isBackgroundTrans = true;

    public AlphaTransformPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);

        // 解决部分手机或高版本手机背景不能变暗BUG, （备注：主题中的windowIsTranslucent true也会影响变暗过程）
        Window window = ((Activity) getContentView().getContext()).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new BitmapDrawable());
        contentView.setFocusableInTouchMode(true);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                showTransBackground(false);
            }
        });
    }

    public void showAtLocation(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showTransBackground(true);
    }

    /**
     * 慎用showAsDropDown
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showTransBackground(true);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showTransBackground(true);
    }


    private void showTransBackground(final boolean isTrans) {
        if (!isBackgroundTrans) {
            return;
        }
        final Window window = ((Activity) getContentView().getContext()).getWindow();
        if (null == window) {
            return;
        }
        // 加上FLAG_DIM_BEHIND标记，解决部分华为手机背景不变暗的BUG，退出时必须clear,否则Activity退出动画会有黑屏一下的情况
        if (isTrans) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        valueAnimator.removeAllListeners();
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                WindowManager.LayoutParams lp = window.getAttributes();
                if (isTrans) {
                    lp.alpha = MAX_ALPHA_VALUE + (1 - MAX_ALPHA_VALUE) * fraction;
                } else {
                    lp.alpha = MAX_ALPHA_VALUE + (1 - fraction) * (1 - MAX_ALPHA_VALUE);
                }

                // 退出动画后
                if (!isTrans && lp.alpha == 1) {
                    //  加上FLAG_DIM_BEHIND标记，解决部分华为手机背景不变暗的BUG，退出时必须clear,否则Activity退出动画会有黑屏一下的情况
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }

                window.setAttributes(lp);
            }
        });

        valueAnimator.start();
    }


}
