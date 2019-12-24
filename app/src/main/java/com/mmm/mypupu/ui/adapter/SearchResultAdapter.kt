package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.goodsNum
import kotlinx.android.synthetic.main.item_recommend.view.*

class SearchResultAdapter (var list: List<Goods>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
        val holder = Holder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods: Goods = list[position]

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
        if (goods.mRemark.isNullOrBlank()) {
            holder.itemView.tvRemark.visibility = View.INVISIBLE
        }
        //点击+ - 事件
        holder.itemView.ivSub.setClickable(true)
        holder.itemView.ivSub.focusable = View.FOCUSABLE
        holder.itemView.requestFocus()
        itemAddClick(holder, position)

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private fun itemAddClick(holder: RecyclerView.ViewHolder, position: Int) {
        var num = 0
        val goods: Goods = list[position]

        holder.itemView.ivAdd.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
                Log.e("点击了：", "+")
                if (num == 0) {
                    num++
                    //   val mAnimation1 = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.sub)

                    val mAnimation3 = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.add)
                    //  holder.itemView.ivSub.startAnimation(mAnimation1)
                    // holder.itemView.tvNum.startAnimation(mAnimation2)
                    holder.itemView.ivAdd.startAnimation(mAnimation3)
                    holder.itemView.tvNum.text = num.toString()
                    holder.itemView.ivSub.x -= 100
                    holder.itemView.tvNum.x -= 68

                } else if (num > 0 && num < goods.mNum) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("数量", holder.itemView.tvNum.text.toString())
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


                    holder.itemView.ivSub.x += 100
                    holder.itemView.tvNum.x += 68
                    holder.itemView.ivAdd.setImageResource(R.drawable.add_able)
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("点击了- ", num.toString())
                } else if (num > 1) {
                    num--
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("数量", holder.itemView.tvNum.text.toString())
                }
            }
        })
    }
}
