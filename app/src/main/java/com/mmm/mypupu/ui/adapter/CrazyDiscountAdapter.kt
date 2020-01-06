package com.mmm.mypupu.ui.adapter


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.util.myUtil
import kotlinx.android.synthetic.main.item_crazy_discount.view.*
import kotlinx.android.synthetic.main.item_recommend.view.*
import kotlinx.android.synthetic.main.item_recommend.view.ivAdd
import kotlinx.android.synthetic.main.item_recommend.view.ivGoods
import kotlinx.android.synthetic.main.item_recommend.view.ivSub
import kotlinx.android.synthetic.main.item_recommend.view.tvNum
import kotlinx.android.synthetic.main.item_recommend.view.tvOriginalPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvQuantity
import kotlinx.android.synthetic.main.item_recommend.view.tvRemark
import kotlinx.android.synthetic.main.item_recommend.view.tvSubtitle
import kotlinx.android.synthetic.main.item_recommend.view.tvTitle
import kotlinx.android.synthetic.main.item_recommend.view.tvY2

class CrazyDiscountAdapter(var list: ArrayList<Goods>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_IMAGE) {
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_crazy_discount_head, parent, false)
            val holder1 = Holder(itemViewImg)
            return holder1
        } else {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_crazy_discount, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods: Goods = list[position]

        if (TYPE_IMAGE == holder.itemViewType) {

        } else if (TYPE_GOODS == holder.itemViewType) {

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
            if (goods.mRemark.isEmpty()) {
                holder.itemView.tvRemark.visibility = View.INVISIBLE
            }
            itemAddClick(holder, position )
            holder.itemView.llItemCrazy.setOnClickListener {
                run {
                    Toast.makeText(context, goods.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return TYPE_IMAGE
        else
            return TYPE_GOODS
    }

    private fun itemAddClick(holder: RecyclerView.ViewHolder, position: Int) {
        var num = 0
        val goods: Goods = list[position]

        holder.itemView.ivAdd.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
              //  Log.e("点击了：", "+")
                if (num == 0) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    itemAddClickAnimator(holder)

                } else if (num > 0 && num < goods.mNum) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    //Log.e("数量", holder.itemView.tvNum.text.toString())
                } else if (num == goods.mNum) {
                    // + 变成灰色
                    holder.itemView.ivAdd.setImageResource(R.drawable.add_unable)
                    holder.itemView.tvNum.text = num.toString()
                    Toast.makeText(holder.itemView.context, "无法购买更多了", Toast.LENGTH_SHORT).show()
                }

            }
        })

        holder.itemView.ivSub.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (num == 1) {
                    //收起 -
                    num--
                    itemSubClickAnimator(holder)
                    holder.itemView.ivAdd.setImageResource(R.drawable.add_able)
                    holder.itemView.tvNum.text = num.toString()
                   // Log.e("点击了- ", num.toString())
                } else if (num > 1) {
                    num--
                    holder.itemView.tvNum.text = num.toString()
                   // Log.e("数量", holder.itemView.tvNum.text.toString())
                }
            }
        })
    }

}
