package me.vast.common.widgets.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator Zz
 * date 2015/10/17
 * Description: SDK5.0之后 v4包的ViewPager onTouch 和其它事件冲突时，有概率会异常
 * <p>
 * 异常信息：
 * (id:2ABAA5A9A7D3AC9A099FE6889AFF4626, errors:[Error(ts:1445062258279, context:java.lang.IllegalArgumentException: pointerIndex out of range
 *   at android.view.MotionEvent.nativeGetAxisValue(Native Method)
 *   at android.view.MotionEvent.getX(MotionEvent.java:1979)
 *   at android.support.v4.view.MotionEventCompatEclair.getX(MotionEventCompatEclair.java:32)
 *   at android.support.v4.view.MotionEventCompat$EclairMotionEventVersionImpl.getX(MotionEventCompat.java:110)
 *   at android.support.v4.view.MotionEventCompat.getX(MotionEventCompat.java:462)
 *   at android.support.v4.view.ViewPager.onInterceptTouchEvent(ViewPager.java:1916)
 */
public class CompatibleViewPager extends ViewPager {
    public CompatibleViewPager(Context context) {
        super(context);
    }

    public CompatibleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception ex) {
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception ex) {
        }
        return false;
    }
}
