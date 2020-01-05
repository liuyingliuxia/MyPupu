package com.mmm.mypupu.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//作为子recycler 可禁用父rec的滑动拦截 (不起作用)
class ChildRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {
    private var lastVisibleItemPosition = 0
    private var firstVisibleItemPosition = 0
    private var mLastY = 0f // 记录上次Y位置
    private var isTopToBottom = false
    private var isBottomToTop = false

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action: Int = event.getAction()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.getY()
                //不允许父View拦截事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val nowY: Float = event.getY()
                isIntercept(nowY)
                if (isBottomToTop || isTopToBottom) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                } else {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                mLastY = nowY
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.onTouchEvent(event)
    }

    override fun isFocused(): Boolean {
        return false
    }

    fun isScrollTop(): Boolean {
        //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        return !canScrollVertically(-1)
    }

    private fun isIntercept(nowY: Float) {
        isTopToBottom = false
        isBottomToTop = false
        val layoutManager: RecyclerView.LayoutManager = getLayoutManager()!!
        if (layoutManager is GridLayoutManager) { //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = (layoutManager as GridLayoutManager)
                .findLastVisibleItemPosition()
            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = (layoutManager as GridLayoutManager)
                .findFirstVisibleItemPosition()
        }
        //得到当前界面可见数据的大小
        val visibleItemCount: Int = layoutManager.getChildCount()
        //得到RecyclerView对应所有数据的大小
        val totalItemCount: Int = layoutManager.getItemCount()
        Log.d("nestScrolling", "onScrollStateChanged")
        if (visibleItemCount > 0) {
            if (lastVisibleItemPosition == totalItemCount - 1) { //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                Log.d("nestScrolling", "触底了")
                if (this@ChildRecyclerView.canScrollVertically(-1) && nowY < mLastY) { // 不能向上滑动
                    Log.d("nestScrolling", "不能向上滑动")
                    isBottomToTop = true
                } else {
                    Log.d("nestScrolling", "向下滑动")
                }
            } else if (firstVisibleItemPosition == 0) { //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                Log.d("nestScrolling", "触顶了")
                if (this@ChildRecyclerView.canScrollVertically(1) && nowY > mLastY) { // 不能向下滑动
                    Log.d("nestScrolling", "不能向下滑动")
                    isTopToBottom = true
                } else {
                    Log.d("nestScrolling", "向上滑动")
                }
            }
        }
    }
}