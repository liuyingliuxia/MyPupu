package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.bumptech.glide.Glide
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.RightItemBean
import kotlinx.android.synthetic.main.item_sort_right_head.view.*
import kotlinx.android.synthetic.main.item_sort_right_item.view.*

//被嵌套的子布局
class SortRightCatalogAdapter  (private val context: Context , private val list: ArrayList<RightItemBean> ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEAD = 0
    private val TYPE_FOOTER = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if( viewType == TYPE_HEAD ){
                val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right_head, parent, false)
                val holder = Holder(itemView)
                return holder
            }else{
                val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right_item, parent, false)
                val holder = Holder(itemView)
                return holder
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder.itemView
        val rightItemBean = list[position]
        Log.e("子 recycler 的 list",rightItemBean.toString())
        if ( TYPE_HEAD == holder.itemViewType)
            Glide.with(context).load(rightItemBean.ivCatalogHead).placeholder(R.drawable.place_holder_banner_home).error(R.drawable.icon_coupon_all_not_usable).into(h.ivCatalogHead)
        else {
            Glide.with(context).load(rightItemBean.imgId).placeholder(R.drawable.place_holder_product_category_image).error(R.drawable.icon_coupon_all_not_usable).into(h.ivSort)
            h.tvSort.setText(rightItemBean.text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 0)
            return TYPE_HEAD
        else
            return TYPE_FOOTER
    }
}