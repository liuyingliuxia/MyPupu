package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.item_search_auto.view.*

class SearchInputAutoAdapter (var list: List<String>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onClick: OnItemClickListener? = null

    fun setOnItemClick(onItemClickListener: OnItemClickListener) {
        this.onClick = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_search_auto, parent, false)
        val holder = Holder(itemView)
        itemView.setOnClickListener{
            onClick!!.OnItemClick(itemView , itemView.tag as Int)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.itemView.tvAutoComplete.text = list[position]
        holder.itemView.tvAutoComplete.setOnClickListener { run{

            }
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int)
    }
}


