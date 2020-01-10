package com.mmm.mypupu.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ParentRecyclerView : RecyclerView {
    constructor( context: Context?) : super(context!!) {}
    constructor( context: Context?,  attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}

    //不拦截，继续分发下去
 /*  override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        Log.e("不拦截，继续分发下去" , "intercept")
        return false
    }*/
}