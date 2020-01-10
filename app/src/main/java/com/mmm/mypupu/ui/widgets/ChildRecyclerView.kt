package com.mmm.mypupu.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//作为子recycler 可禁用父rec的滑动拦截
class ChildRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {
    private var lastVisibleItemPosition = 0
    private var firstVisibleItemPosition = 0
    private var mLastY = 0f // 记录上次Y位置
    private var isTopToBottom = false
    private var isBottomToTop = false
    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        val action = e!!.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = e.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val nowY = e.y
             //   isIntercept(nowY) //判断是否到顶 或到底
                if (isBottomToTop || isTopToBottom) { //触底或 到顶 时 交给父容器拦截
                    parent.requestDisallowInterceptTouchEvent(false) // false 允许 ，true 不允许
                } else {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                mLastY = nowY
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
            }
        return super.onTouchEvent(e)
    }

   /* private fun isIntercept(nowY: Float) {
        isTopToBottom = false
        isBottomToTop = false
        val layoutManager: LayoutManager = getLayoutManager()!!

        //得到当前界面可见数据的大小
        val visibleItemCount: Int = layoutManager.childCount

        //得到RecyclerView对应所有数据的大小
        val totalItemCount: Int = layoutManager.itemCount

        if (layoutManager is GridLayoutManager) {
            //得到当前界面，最后一个子视图对应的position
            lastVisibleItemPosition = (layoutManager).findLastVisibleItemPosition()

            //得到当前界面，第一个子视图的position
            firstVisibleItemPosition = (layoutManager).findFirstVisibleItemPosition()
        }

        if (visibleItemCount > 0) {
            if (lastVisibleItemPosition == totalItemCount ) {
                //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                Log.e("nestScrolling", "触底了")
                //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                if (this@ChildRecyclerView.canScrollVertically(-1) && nowY < mLastY) {
                    // 不能向上滑动
                    Log.e("nestScrolling", "不能向上滑动")
                    isBottomToTop = true
                } else {
                    Log.e("nestScrolling", "向下滑动")
                }
            } else if (firstVisibleItemPosition == 0) {
                //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                Log.d("nestScrolling", "触顶了")
                if (this@ChildRecyclerView.canScrollVertically(1) && nowY > mLastY) {
                    // 不能向下滑动
                    Log.e("nestScrolling", "不能向下滑动")
                    isTopToBottom = true
                } else {
                    Log.e("nestScrolling", "向上滑动")
                }
            }
        }
    }*/
}