package com.mmm.mypupu.ui.adapter

import android.bluetooth.BluetoothClass
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.data.mAllName1
import com.mmm.mypupu.ui.data.mCatalogText
import com.mmm.mypupu.ui.data.mSortHeadTag
import com.mmm.mypupu.util.mT
import kotlinx.android.synthetic.main.item_sort_content.view.*
import kotlinx.android.synthetic.main.item_sort_left.view.*

class SortLeftAdapter  (private val context: Context , private val list: ArrayList<String> , private val rvRight:RecyclerView):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEAD = 0
    private val TYPE_FOOTER = 1
    private var CHECK_STATE = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEAD) {
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_sort_left, parent, false)
            val holder1 = Holder(itemViewImg)
            return holder1
        } else {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_catalog_empty, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun getItemViewType(position: Int): Int {
        if (position == mCatalogText.size.minus(1))
            return TYPE_FOOTER
        else
            return TYPE_HEAD
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val llm : LinearLayoutManager = rvRight.layoutManager as LinearLayoutManager
        val h = holder.itemView
        if (TYPE_FOOTER == holder.itemViewType) {

        } else {
            h.tvCatalog.setText(list[position])
            h.tvCatalog.setOnClickListener {
                kotlin.run {
                    llm.scrollToPositionWithOffset(position, 0)
                    llm.stackFromEnd = false
                    h.tvCatalog.isEnabled = false
                    CHECK_STATE = position
                    notifyDataSetChanged()
                }
            }
            if ( CHECK_STATE == position )
            {  //滑到的对应item
                h.tvCatalog.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                val mSize: Float =context.resources.getDimension(R.dimen.mmm_font_s5)
                h.tvCatalog.setTextSize(mSize)
                h.tvCatalog.isEnabled = false
            }
            else {//未滑到
                h.tvCatalog.setTypeface(Typeface.DEFAULT)
                val mSizeNor: Float =context.resources.getDimension(R.dimen.mmm_font_s4)
                h.tvCatalog.setTextSize(mSizeNor)
                h.tvCatalog.isEnabled = true
            }
        }
    }
}