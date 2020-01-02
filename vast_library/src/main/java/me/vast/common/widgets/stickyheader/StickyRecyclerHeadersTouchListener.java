package me.vast.common.widgets.stickyheader;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

public class StickyRecyclerHeadersTouchListener implements RecyclerView.OnItemTouchListener {
        private final GestureDetector mTapDetector;
    private final RecyclerView mRecyclerView;
    private final StickyRecyclerHeadersDecoration mDecor;
    private OnHeaderClickListener mOnHeaderClickListener;

    public interface OnHeaderClickListener {
        void onHeaderClick(View header, int position, long headerId);
    }

    public StickyRecyclerHeadersTouchListener(final RecyclerView recyclerView,
                                              final StickyRecyclerHeadersDecoration decor) {
        mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        mRecyclerView = recyclerView;
        mDecor = decor;
    }

    public StickyRecyclerHeadersAdapter getAdapter() {
        if (mRecyclerView.getAdapter() instanceof StickyRecyclerHeadersAdapter) {
            return (StickyRecyclerHeadersAdapter) mRecyclerView.getAdapter();
        } else {
            throw new IllegalStateException("A RecyclerView with " +
                StickyRecyclerHeadersTouchListener.class.getSimpleName() +
                " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
        }
    }


    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        mOnHeaderClickListener = listener;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        if (this.mOnHeaderClickListener != null) {
//            int action = e.getAction();
//            if (action == MotionEvent.ACTION_DOWN) {
//                mPosition = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
//                Log.e("testzzz", "  MotionEvent.ACTION_DOWN  = " + mPosition);
//            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
//                if (-1 != mPosition) {
//                    int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
//                    Log.e("testzzz", "  MotionEvent.ACTION_UP  = " + position);
//
//                    if (-1 == position) {
//                        mPosition = -1;
//                    } else {
//                        View headerView = mDecor.getHeaderView(mRecyclerView, position);
//                        long headerId = getAdapter().getHeaderId(position);
//                        mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
//                        mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
//                        headerView.onTouchEvent(e);
//
//
//                        handleEventToChild(headerView, (int) e.getX(), (int) e.getY());
//                    }
//                }
//            }

            boolean tapDetectorResponse = this.mTapDetector.onTouchEvent(e);
            if (tapDetectorResponse) {
                // Don't return false if a single tap is detected
                return true;
            }
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
                return position != -1;
            }
        }
        return false;
    }

//
//    private void handleEventToChild(View headerView, int x, int y) {
//        // zhengz 触发子控件事件
//        if (headerView instanceof ViewGroup) {
//            ViewGroup header = (ViewGroup) headerView;
//            View child;
//
//            Rect headRect = mDecor.findHeaderPositionUnderRect(x, y);
//            Rect childRect = new Rect();
//            Rect absRect = new Rect();
//
//            for (int i = 0; i < header.getChildCount(); i++) {
//                child = header.getChildAt(i);
//                if (!child.hasOnClickListeners()) {
//                    continue;
//                }
//                child.getHitRect(childRect);
//
//                absRect.left = headRect.left + childRect.left;
//                absRect.top = headRect.top + childRect.top;
//                absRect.right = absRect.left + childRect.width();
//                absRect.bottom = absRect.top + childRect.height();
//
//                if (absRect.contains(x, y)) {
//                    // 特殊处理，child不存在当前的View树中，外部测量不出当前View的坐标位置
//                    child.setTag(absRect);
//                    child.performClick();
//                    break;
//                }
//            }
//        }
//
//    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent e) {
        if (this.mOnHeaderClickListener != null) {
            mTapDetector.onTouchEvent(e);
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getHeaderView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
                mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
                headerView.onTouchEvent(e);
                return true;
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}
