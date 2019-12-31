package com.mmm.mypupu.ui.adapter

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.LeftBean
import kotlinx.android.synthetic.main.item_sort_left.view.*

class QuickLeftAdapter ( var layoutId :Int, var datas:List<LeftBean> ) :BaseQuickAdapter<LeftBean , BaseViewHolder> (layoutId , datas) {
    var index = -1
    override fun convert(helper: BaseViewHolder?, item: LeftBean?) {
        helper!!.getView<TextView>(R.id.tvCatalog).setText(item!!.name)
        if ( index == item.id) {//选中
            helper.getView<ConstraintLayout>(R.id.rvLeft).setBackgroundResource(R.drawable.catalog_item_bg)
        }else{ // 未选中
            helper.getView<ConstraintLayout>(R.id.rvLeft).setBackgroundColor(mContext.getColor(R.color.color8))
        }
    }

}