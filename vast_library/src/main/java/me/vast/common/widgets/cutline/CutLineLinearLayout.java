package me.vast.common.widgets.cutline;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * author : zhengz
 * time   : 2018/3/7
 * desc   : layout布局中可以水平、垂直的分割线条，支持线条数量、颜色、粗度、以及前后margin偏移量
 */

public class CutLineLinearLayout extends LinearLayout {
    private final CutBehaviorLine mBehaviorLine = CutBehaviorLine.create();

    public CutLineLinearLayout(Context context) {
        this(context, null);
    }

    public CutLineLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CutLineLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBehaviorLine.init(this, attrs);
    }

    /**
     * 隐藏线
     *
     * @param orientation
     */
    public void hideGravity(int orientation) {
        mBehaviorLine.hideOrientation(orientation);
    }

    /**
     * 显示线
     *
     * @param orientation
     */
    public void showGravity(int orientation) {
        mBehaviorLine.showOrientation(orientation);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mBehaviorLine.drawLine(canvas);
    }

}
