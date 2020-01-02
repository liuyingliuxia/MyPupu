package me.vast.common.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 带有颜色蒙层布局
 * Created by zhengz on 2017/7/17.
 */

public class MaskingFrameLayout extends FrameLayout {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mMaskColor;


    public MaskingFrameLayout(Context context) {
        super(context);
    }

    public MaskingFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskingFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean issued = super.drawChild(canvas, child, drawingTime);
        if (mMaskColor != 0) {
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        }
        return issued;
    }

    public void setMaskColor(int res) {
        if (mMaskColor != res) {
            mMaskColor = res;
            if (res != 0) {
                mPaint.setColor(getResources().getColor(res));
            }
            invalidate();
        }
    }
}
