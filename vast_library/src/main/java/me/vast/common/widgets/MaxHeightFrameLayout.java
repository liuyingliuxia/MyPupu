package me.vast.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import me.vast.common.R;


/**
 * Created by zhengz on 2017/8/23.
 */

public class MaxHeightFrameLayout extends FrameLayout {
    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;

    private float mMaxRatio = DEFAULT_MAX_RATIO;  // 优先级高
    private float mMaxHeight = DEFAULT_MAX_HEIGHT; // 优先级低
    /**
     * 移动限制最大高度
     */
    private boolean isEnabledMaxHeight = true;


    public MaxHeightFrameLayout(Context context) {
        this(context, null);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        init();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightView);
        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.MaxHeightView_mhv_HeightRatio) {
                mMaxRatio = a.getFloat(attr, DEFAULT_MAX_RATIO);
            } else if (attr == R.styleable.MaxHeightView_mhv_HeightDimen) {
                mMaxHeight = a.getDimension(attr, DEFAULT_MAX_HEIGHT);
            }
        }
        a.recycle();
    }

    private void init() {
        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * getScreenHeight(getContext());
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * getScreenHeight(getContext()));
        }
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;
    }

    public void setMaxHeightRatio(float ratio) {
        if (mMaxRatio != ratio) {
            mMaxRatio = ratio;
            init();
            requestLayout();
        }
    }

    public void onEnabledMaxHeight(boolean enabled) {
        if (isEnabledMaxHeight != enabled) {
            isEnabledMaxHeight = enabled;
            requestLayout();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isEnabledMaxHeight) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     */
    private int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
