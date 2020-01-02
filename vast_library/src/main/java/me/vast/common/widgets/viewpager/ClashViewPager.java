package me.vast.common.widgets.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ClashViewPager extends CompatibleViewPager {

    private final int OFFSET_CLICK_RANGE = 5;

    private int downX;
    private int downY;

//    private int scrollState = ViewPager.SCROLL_STATE_IDLE;

    public ClashViewPager(Context context) {
        super(context);
        this.setOnTouchListener(onTouchLis);
    }

    public ClashViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(onTouchLis);
//        this.addOnPageChangeListener(new OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                scrollState = state;
//            }
//        });
    }


    private OnTouchListener onTouchLis = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs((int) event.getY() - downY) < Math.abs((int) event.getX() - downX)) {
                        requestDisallowInterceptTouchEvent(true);
                    } else {
                        requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    requestDisallowInterceptTouchEvent(false);
//                    if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                    if (Math.abs((int) event.getX() - downX) <= OFFSET_CLICK_RANGE && Math.abs((int) event.getY() - downY) <= OFFSET_CLICK_RANGE) {
                        if (null != iSlideLis) {
                            iSlideLis.onClick(getCurrentItem());
                        }
                    }
//                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        }
    };


    private IViewListener iSlideLis;

    public void setIViewListener(IViewListener iSlideLis) {
        this.iSlideLis = iSlideLis;
    }


    public interface IViewListener {
        void onClick(int position);
    }


}
