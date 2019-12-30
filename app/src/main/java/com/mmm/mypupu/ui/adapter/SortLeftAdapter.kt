package com.mmm.mypupu.ui.adapter

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
    private var CHECK_STATE = -1
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
        val h = holder.itemView
        h.tvCatalog.tag = 0
        if (TYPE_FOOTER == holder.itemViewType) {

        } else {
            h.tvCatalog.setText(list[position])
//            if ()
            h.tvCatalog.setOnClickListener {
                kotlin.run {
//                    h.tvCatalog.isEnabled = false
                    h.tvCatalog.tag = 1
                    stateCheack(holder)
                }
            }

        }
    }

    fun setDefSelect ( position: Int) {
        CHECK_STATE = position
    }
    fun stateCheack (holder:RecyclerView.ViewHolder){
        val h = holder.itemView
        if (    CHECK_STATE == 0 ) {//未选中
            h.tvCatalog.setTypeface(Typeface.DEFAULT)
            val mSizeNor: Float =context.resources.getDimension(R.dimen.mmm_font_s4)
            h.tvCatalog.setTextSize(mSizeNor)
            h.tvCatalog.isEnabled = true
        }else // 选中
        {
            h.tvCatalog.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            val mSize: Float =context.resources.getDimension(R.dimen.mmm_font_s5)
            h.tvCatalog.setTextSize(mSize)
            h.tvCatalog.isEnabled = false
            CHECK_STATE = 1
        }
    }

}