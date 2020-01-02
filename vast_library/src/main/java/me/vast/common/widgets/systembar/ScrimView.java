package me.vast.common.widgets.systembar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : zhengz
 * time   : 2018/1/4
 * desc   :
 */

public class ScrimView extends View {
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;

    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private float mScrimOpacity;
    private Paint mScrimPaint = new Paint();

    public ScrimView(Context context) {
        super(context);
    }

    public ScrimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrimOpacity(float scrimOpacity) {
        if (mScrimOpacity != scrimOpacity) {
            mScrimOpacity = scrimOpacity;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mScrimOpacity > 0) {
            final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
            final int imag = (int) (baseAlpha * mScrimOpacity);
            final int color = imag << 24 | (mScrimColor & 0xffffff);
            mScrimPaint.setColor(color);

            canvas.drawRect(0, 0,  getWidth(), getHeight(), mScrimPaint);
        }
    }








}
