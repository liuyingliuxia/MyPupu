package com.mmm.mypupu.ui.adapter

import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.LeftBean
import kotlinx.android.synthetic.main.item_sort_left.view.*
//左侧目录 + 点击事件
class QuickLeftAdapter(data :MutableList<LeftBean>) : BaseMultiItemQuickAdapter<LeftBean, BaseViewHolder>(data) {
    var index = -1

    init {
      addItemType(LeftBean.TYPE_TEXT , R.layout.item_sort_left)
      addItemType(LeftBean.TYPE_EMPTY , R.layout.item_sort_catalog_empty)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun convert(holder: BaseViewHolder?, item: LeftBean?) {
        val mTextView = holder!!.getView<TextView>(R.id.tvCatalog)
        when (holder.itemViewType) {
            LeftBean.TYPE_TEXT -> {
                mTextView.setText(item!!.text)
                if ( index == item.id) {
                    mTextView.setBackgroundResource(R.drawable.catalog_item_bg)
                    mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    mTextView.setTextColor(ContextCompat.getColor(mContext,R.color.color23))
                    val mSizeNor: Float =mContext.resources.getDimension(R.dimen.mmm_font_s5)
                    mTextView.setTextSize(mSizeNor)

                }else{
                    mTextView.setBackgroundColor(mContext.getColor(R.color.color8))
                    mTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
                    val mSizeNor: Float =mContext.resources.getDimension(R.dimen.mmm_font_s4)
                    mTextView.setTextColor(ContextCompat.getColor(mContext,R.color.color3))
                    mTextView.setTextSize(mSizeNor)
                    // mTextView.isEnabled = false
                }
            }
            LeftBean.TYPE_EMPTY -> {
                holder.getView<LinearLayout>(R.id.llEmpty).isEnabled = false
                holder.getView<LinearLayout>(R.id.llEmpty).isClickable = false
            }
        }
    }
}