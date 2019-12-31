package com.mmm.mypupu.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue
import kotlin.math.min

object MyUtils {
    fun moveToMiddle ( recyclerView: RecyclerView , position :Int) {
        val manager = recyclerView.layoutManager as GridLayoutManager
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        //中间位置
        val middle = ( firstItem + lastItem ) / 2
        val index = ( position - middle ).absoluteValue
        if ( index >= recyclerView.childCount) {
            recyclerView.scrollToPosition( position )
        }else{
            if ( position < middle ){
                recyclerView.scrollBy( 0 ,-recyclerView.getChildAt(index).top)
            }else{
                recyclerView.scrollBy( 0 ,recyclerView.getChildAt(index).top)
            }
        }
    }
}