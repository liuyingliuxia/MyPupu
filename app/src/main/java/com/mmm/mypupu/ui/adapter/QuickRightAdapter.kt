package com.mmm.mypupu.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mmm.mypupu.ui.bean.RightBean2

class QuickRightAdapter( layoutId:Int, datas:List<RightBean2>): BaseQuickAdapter<RightBean2, BaseViewHolder>(layoutId,datas) {


    override fun convert(helper: BaseViewHolder?, item: RightBean2?) {

        //helper!!.getView<ImageView>(ivHead).setImageResource(item!!.imgHeadId)
    }
}