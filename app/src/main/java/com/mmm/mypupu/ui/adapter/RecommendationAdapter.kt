package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.item_recommend.view.*

class RecommendationAdapter (var list: List<Goods>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>(),View.OnClickListener {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if ( viewType == TYPE_IMAGE ){
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_recommend_head, parent,false)
            val holder1 = Holder(itemViewImg)
                return holder1
         }

        else  {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
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
            if (goods.mRemark.isNullOrBlank()){
                holder.itemView.tvRemark.visibility = View.INVISIBLE
            }

            holder.itemView.ivAdd.setOnClickListener( object :View.OnClickListener{
                override fun onClick(v: View?) {
                    val mAnimation1 = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.sub)
                    val mAnimation2 = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.num)
                    val mAnimation3 = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.add)
                    holder.itemView.ivSub.startAnimation(mAnimation1)
                    holder.itemView.tvNum.startAnimation(mAnimation2)
                    holder.itemView.ivAdd.startAnimation(mAnimation3)
                   // holder.itemView.tvNum.text
                }
            })


            holder.itemView.ivSub.setOnClickListener( object :View.OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(context,"-",Toast.LENGTH_SHORT ).show()
                }
            })

        }
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 0)
            return TYPE_IMAGE
        else
            return TYPE_GOODS

   }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun translateImageView() {

    }

}