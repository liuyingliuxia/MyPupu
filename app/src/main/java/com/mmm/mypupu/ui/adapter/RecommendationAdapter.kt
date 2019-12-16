package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.item_recommend.view.*

class RecommendationAdapter (var list: MutableList<String>, var context: Context  ):RecyclerView.Adapter <RecyclerView.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
        val holder = Holder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      holder.itemView.tvPrice.text = list[position]
      holder.itemView.tag = position

        //给文字添加删除线
      holder.itemView.tvY2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
      holder.itemView.tvOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


}