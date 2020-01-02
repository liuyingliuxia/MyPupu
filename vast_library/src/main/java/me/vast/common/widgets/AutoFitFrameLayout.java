package me.vast.common.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * zhengz
 */
public class AutoFitFrameLayout extends FrameLayout {

    private float mRatioW;
    private float mRatioH;
    /**
     * 默認固定寬度（计算等比高度），同理反之
     */
    private FitMode mFitMode = FitMode.FIX_WIDTH;

    public AutoFitFrameLayout(Context context) {
        this(context, null);
    }

    public AutoFitFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRatioSize(float rationW, float ratioH) {
        setRatioSize(rationW, ratioH, FitMode.FIX_WIDTH);
    }

    public void setRatioSize(float rationW, float ratioH, FitMode mode) {
        mRatioW = rationW;
        mRatioH = ratioH;
        mFitMode = mode;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (mRatioW > 0 && mRatioH > 0) {
            if (mFitMode == FitMode.FIX_WIDTH) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) (width * mRatioH / mRatioW);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            } else {
                int height = MeasureSpec.getSize(heightMeasureSpec);
                int width = (int) (height * mRatioW / mRatioH);
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
