package com.mmm.mypupu.ui.adapter

import android.graphics.Typeface
import android.os.Build
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.SearchHistoryBean

class QuickHistoryAdapter(var layoutId :Int , data :ArrayList<SearchHistoryBean>) :
    BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(layoutId , data) {

    override fun convert(holder: BaseViewHolder?, item: SearchHistoryBean?) {
        val mTextView = holder!!.getView<TextView>(R.id.tvHistoryItem)
        mTextView.text = item!!.title
    }
}