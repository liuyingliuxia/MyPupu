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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_catalog, parent,false)
        val holder = Holder(itemView)
        itemView.setOnClickListener{
            onClick!!.OnItemClick (itemView , itemView.tag as Int)
        }
        return holder
    }

    override fun getItemCount(): Int {
        Log.e("list长度",list.size.toString())
        return list.size
      }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val item = list[position]
        holder.itemView.tvCatalog.text = item
        holder.itemView.tag = position

        holder.itemView.llCatalog.setOnClickListener { v -> run{
            holder.itemView.llCatalog.background =holder.itemView. resources.getDrawable(R.color.color1)
            holder.itemView.ivVerCutLine.visibility = View.VISIBLE
            holder.itemView.tvCatalog.setTextColor(holder.itemView.resources.getColor(R.color.color23))
            holder.itemView.tvCatalog.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
         }
        }
    }

    //整个item 点击事件的方法
    var onClick :OnItemClickListener ? = null
    fun setOnItemClick (onItemClickListener :OnItemClickListener) {
        this.onClick = onItemClickListener
    }

    //整个item 点击事件的接口
    interface OnItemClickListener {
        fun OnItemClick (view : View ,position: Int)
    }
}