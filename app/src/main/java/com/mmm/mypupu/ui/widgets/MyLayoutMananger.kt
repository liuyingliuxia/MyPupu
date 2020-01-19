package com.mmm.mypupu.ui.widgets

import android.view.View
import androidx.recyclerview.widget.RecyclerView
// 可使 水平item 自动换行的 layoutManager 用于历史记录的展示
class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)
        val sumWidth: Int = width
        var curLineWidth = 0
        var curLineTop = 10
        var lastLineMaxHeight = 0
        for (i in 0 until itemCount) {
            val view: View = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width: Int = getDecoratedMeasuredWidth(view)
            val height: Int = getDecoratedMeasuredHeight(view)
            curLineWidth += width
            if (curLineWidth <= sumWidth) { //不需要换行
                layoutDecorated(view, curLineWidth - width, curLineTop, curLineWidth, curLineTop + height)
                //比较当前行多有item的最大高度
                lastLineMaxHeight = lastLineMaxHeight.coerceAtLeast(height)
            } else { //换行
                curLineWidth = width
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height
                }
                //记录当前行top
                curLineTop += lastLineMaxHeight
                layoutDecorated(view, 0, curLineTop, width, curLineTop + height)
                lastLineMaxHeight = height
            }
        }
    }
}