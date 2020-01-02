package me.vast.common.widgets.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * author : zhengz
 * time   : 2018/1/9
 * desc   : 禁止手动滑动翻页
 */

public class SwipeViewPager extends ViewPager {
    private boolean swipePageChangeEnabled;

    private float downX, downY;
    private final int mTouchSlop;
    private boolean isDraggerHorizontal;


    public SwipeViewPager(@NonNull Context context) {
        this(context, null);
    }

    public SwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.swipePageChangeEnabled) {
            return false;
        }

        if (!isDraggerHorizontal) {
            return false;
        }

        return this.swipePageChangeEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.swipePageChangeEnabled) {
            return false;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                downX = ev.getX();
                isDraggerHorizontal = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isDraggerHorizontal) {
                    break;
                }
                float distanceX = Math.abs(ev.getX() - downX);
                float distanceY = Math.abs(ev.getY() - downY);
                if (distanceY > mTouchSlop || distanceX > mTouchSlop) {
                    if (distanceX < distanceY) {
                        isDraggerHorizontal = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDraggerHorizontal = true;
                break;
        }
        if (!isDraggerHorizontal) {
            return false;
        }
        return this.swipePageChangeEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        if (!this.swipePageChangeEnabled) {
            return false;
        }
        return this.swipePageChangeEnabled && super.executeKeyEvent(event);
    }

    public void onOffsetChanged(int verticalOffset) {
        if (!swipePageChangeEnabled) {
            return;
        }
        verticalOffset = Math.abs(verticalOffset);
        if (verticalOffset > mTouchSlop) {
            isDraggerHorizontal = false;
        }
    }

    public void setPagingEnabled(boolean enabled) {
        this.swipePageChangeEnabled = enabled;
    }

}
