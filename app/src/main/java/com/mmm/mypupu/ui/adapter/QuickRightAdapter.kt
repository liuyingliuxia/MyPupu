package com.mmm.mypupu.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.R
import com.mmm.mypupu.R.id.ivHead
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.bean.RightBean
import kotlinx.android.synthetic.main.item_sort_content.view.*

class QuickRightAdapter( layoutId:Int, datas:List<RightBean>): BaseQuickAdapter<RightBean, BaseViewHolder>(layoutId,datas) {


    override fun convert(helper: BaseViewHolder?, item: RightBean?) {

        helper!!.getView<ImageView>(ivHead).setImageResource(item!!.imgHeadId)
    }
}