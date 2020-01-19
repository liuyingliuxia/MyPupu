package com.mmm.mypupu.ui.widgets

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.util.MyUtil

//设置item的水平边距
class HorizontalItemDecoration(space: Int, mContext: Context) : RecyclerView.ItemDecoration() {
    //定义2个Item之间的距离
    private val space: Int = MyUtil.dip2px(space.toFloat(), mContext)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        val totalCount: Int = parent.adapter!!.itemCount
//        if (position == 0) { //第一个
//            outRect.left = 0
//            outRect.right = space / 2
//        } else if (position == totalCount - 1) { //最后一个
//            outRect.left = space / 2
//            outRect.right = 0
//        } else { //中间其它的
        outRect.left = space / 3
        outRect.right = space / 3
        outRect.top = space / 2
        outRect.bottom = space / 2
        //    }
    }


}

