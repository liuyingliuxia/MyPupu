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
import kotlinx.android.synthetic.main.item_sort_content.view.*

class SortContentAdapter (private val context: Context , private val list: ArrayList<Int> ):RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_content, parent, false)
            val holder0 = Holder(itemView)
            return holder0

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
         holder.itemView.ivContent.setImageResource(item)
         holder.itemView.tag = position
        }
}