package com.mmm.mypupu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_sort_catalog.view.*

class SortCatalogAdapter (private val context: Context , private val list: ArrayList<String> ):RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private val ITEM_NAME = 0
    private val EMPTY = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if ( viewType == 0 ) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_catalog, parent, false)
            val holder0 = Holder(itemView)
            return holder0
        }
        else
        {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_catalog_empty, parent, false)
            val holder1 =  Holder(itemView)
            return holder1
        }
    }

    override fun getItemCount(): Int {
        Log.e("list长度",list.size.toString())
        return list.size
      }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if ( ITEM_NAME == holder.itemViewType){
        holder.itemView.tvCatalog.text = item
        holder.itemView.tag = position
        holder.itemView.llCatalog.setOnClickListener {
            run {
                holder.itemView.llCatalog.background = holder.itemView.resources.getDrawable(R.color.color1)
                holder.itemView.ivVerCutLine.visibility = View.VISIBLE
                holder.itemView.tvCatalog.setTextColor(holder.itemView.resources.getColor(R.color.color23))
                holder.itemView.tvCatalog.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                var mSize: Float = holder.itemView.resources.getDimension(R.dimen.mmm_font_s5)
                holder.itemView.tvCatalog.setTextSize(mSize)
               }
            }
           }

    }

    //整个item 点击事件的方法
    var onClick :OnItemClickListener ? = null
    fun setOnItemClick (onItemClickListener :OnItemClickListener) {
        this.onClick = onItemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 17)
            return EMPTY
        else
            return ITEM_NAME
    }

    //整个item 点击事件的接口
    interface OnItemClickListener {
        fun OnItemClick (view : View ,position: Int)
    }
}