package com.mmm.mypupu.ui.adapter

import android.bluetooth.BluetoothClass
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.RightItemBean
import kotlinx.android.synthetic.main.item_recommend.view.*
import kotlinx.android.synthetic.main.item_sort__right2_item.view.*


class SortRightCatalogAdapter  (private val context: Context , private val list: ArrayList<RightItemBean> ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right2, parent, false)
            val holder = Holder(itemView)
            return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder.itemView
        val rightItemBean = list[position]
        Glide.with(mContext).load(rightItemBean.imgId).placeholder(R.drawable.place_holder_product_category_image).error(R.drawable.icon_coupon_all_not_usable).into(h.ivSort)
    }
}