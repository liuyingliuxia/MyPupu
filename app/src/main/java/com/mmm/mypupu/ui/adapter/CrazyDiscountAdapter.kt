package com.mmm.mypupu.ui.adapter


import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.item_recommend.view.*

class CrazyDiscountAdapter (var list: List<Goods>, var context: Context  ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if ( viewType == TYPE_IMAGE ){
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_crazy_discount_img, parent,false)
            val holder1 = Holder(itemViewImg)
            return holder1
        }

        else  {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_crazy_discount, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods: Goods = list[position ]

        if (TYPE_IMAGE ==  holder.itemViewType) {

        }else if(TYPE_GOODS == holder.itemViewType){

            holder.itemView.tag = position

            holder.itemView.ivGoods.setImageResource(goods.mImgPath)
            holder.itemView.tvTitle.text = goods.mName
            holder.itemView.tvSubtitle.text = goods.mSubtitle
            holder.itemView.tvQuantity.text = goods.mQuantity
            holder.itemView.tvRemark.text = goods.mRemark
            holder.itemView.tvPrice.text = goods.mPrice.toString()
            holder.itemView.tvOriginalPrice.text = goods.mOriPrice.toString()
            //给文字添加删除线
            holder.itemView.tvY2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.tvOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        }
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 0)
            return TYPE_IMAGE
        else
            return TYPE_GOODS

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

}