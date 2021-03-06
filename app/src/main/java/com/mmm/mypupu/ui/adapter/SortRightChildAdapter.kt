package com.mmm.mytestutil.rvInRecycler

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.HeadImgBean
import com.mmm.mypupu.ui.bean.ItemBean
import com.mmm.mypupu.util.MyUtil
import kotlinx.android.synthetic.main.item_sort_right_head.view.*
import kotlinx.android.synthetic.main.item_sort_right_item.view.*

//子适配器

//分类页面 最内层的 adapter
class SortRightChildAdapter(var head: HeadImgBean, var listFoot: List<ItemBean>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_ITEM = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_IMAGE) {
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_sort_right_head, parent, false)
            val holder1 = Holder(itemViewImg)
            return holder1
        } else {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right_item, null, false)
            val holder2 = Holder(itemView)
            return holder2
        }
    }

    override fun getItemCount(): Int {
        if (listFoot.size > 0) {
            return listFoot.size + 1
        } else
            return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Log.e("size = " , listFoot.size.toString() )
        //  Log.e ("position = " ,position.toString())
        var item: ItemBean = ItemBean(0, 0, "")

        if (position > 0) {
            item = listFoot[position - 1]
        }

        val h = holder.itemView

        // Log.e("item ", item.toString())
        if (TYPE_IMAGE == holder.itemViewType) {
            Glide.with(context).load(head.imgId).placeholder(R.drawable.place_holder_banner_home).error(R.drawable.icon_coupon_all_not_usable).into(h.ivCatalogHead)
            h.ivCatalogHead.setOnClickListener {
                MyUtil.talk(context, head.tag)
            }
        } else if (TYPE_ITEM == holder.itemViewType) {
            Glide.with(context).load(item.imgId).placeholder(R.drawable.place_holder_banner_home).error(R.drawable.icon_coupon_all_not_usable).into(h.ivSort)
            h.tvSort.setText(item.text)
            h.llSort.setOnClickListener {
                MyUtil.talk(context, item.toString())
            }
            if (item.id == -1 ){
                h.tvSort.visibility = View.INVISIBLE
                h.ivSort.visibility = View.INVISIBLE
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return TYPE_IMAGE
        else
            return TYPE_ITEM
    }

}

