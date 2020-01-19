package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.GoodsBean
import com.mmm.mypupu.util.MyUtil.Companion.itemAddClickAnimator
import com.mmm.mypupu.util.MyUtil.Companion.itemSubClickAnimator
import kotlinx.android.synthetic.main.item_recommend.view.*

class FruitAdapter(var list: ArrayList<GoodsBean>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
        val holder = Holder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods: GoodsBean = list[position]

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

        itemAddClick(holder , goods)

        holder.itemView.llItemGoods.setOnClickListener {
            run {
                Toast.makeText(context, goods.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

    private fun itemAddClick(holder: RecyclerView.ViewHolder , goods: GoodsBean) {
        var num = 0
        holder.itemView.ivAdd.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
                Log.e("点击了：", "+")
                if (num == 0) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    itemAddClickAnimator(holder)

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
                    itemSubClickAnimator(holder)
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
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
