package com.mmm.mypupu.ui.adapter

import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.LeftBean
import kotlinx.android.synthetic.main.item_sort_left.view.*

class QuickLeftAdapter (  layoutId :Int,  datas:List<LeftBean> ) :BaseQuickAdapter<LeftBean , BaseViewHolder> (layoutId , datas) {
    var index = -1
    @RequiresApi(Build.VERSION_CODES.M)
    override fun convert(helper: BaseViewHolder?, item: LeftBean?) {
        val mTextView = helper!!.getView<TextView>(R.id.tvCatalog)
        if ( index == item!!.id) {
            mTextView.setBackgroundResource(R.drawable.catalog_item_bg)
            mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            mTextView.isEnabled = false

        }else{
            mTextView.setBackgroundColor(mContext.getColor(R.color.color8))
            mTextView.isEnabled = true
        }
    }
}