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
import com.mmm.mypupu.util.MyUtil
import kotlinx.android.synthetic.main.item_recommend.view.*

class RecommendationAdapter(var list: ArrayList<GoodsBean>, var context: Context, var count :Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1
    private val TYPE_END = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_IMAGE) {
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_recommend_head, parent, false)
            val holder1 = Holder(itemViewImg)
            return holder1
        } else if (viewType == TYPE_GOODS) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        } else  {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_no_more, parent, false)
            val holder3 = Holder(itemView)
            return holder3
        }
    }

    override fun getItemCount(): Int {
        if (list.size > 0) {
            return list.size + 2
        } else
            return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0 && position != itemCount - 1 ) {
            val goods: GoodsBean = list[position - 1]
            if (TYPE_GOODS == holder.itemViewType) {
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
                //点击+ - 事件
                holder.itemView.ivSub.setClickable(true)
                holder.itemView.ivSub.focusable = View.FOCUSABLE
                holder.itemView.requestFocus()
                itemAddClick(holder, position )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return TYPE_IMAGE
        else if (position == itemCount - 1 )
            return TYPE_END
        else
            return TYPE_GOODS
    }

    private fun itemAddClick(holder: RecyclerView.ViewHolder, position: Int) {
        var num = 0
        var goods: GoodsBean = GoodsBean(0, "", "", "", "", 0.0, 0.0, 0)
        if (list.size > 0) {
            goods = list[position - 1]
        }
        holder.itemView.llItemGoods.setOnClickListener {
                Toast.makeText(context, goods.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.itemView.ivAdd.setOnClickListener{
            @RequiresApi(Build.VERSION_CODES.O)
                //  Log.e("点击了：", "+")
                if (num == 0) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    MyUtil.itemAddClickAnimator(holder)
                } else if (num > 0 && num < goods.mNum) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    //  Log.e("数量", holder.itemView.tvNum.text.toString())
                } else if (num == goods.mNum) {
                    // + 变成灰色
                    holder.itemView.ivAdd.setImageResource(R.drawable.add_unable)
                    holder.itemView.tvNum.text = num.toString()
                    Toast.makeText(holder.itemView.context, "无法购买更多了", Toast.LENGTH_SHORT).show()
                }
        }

        holder.itemView.ivSub.setOnClickListener{
                if (num == 1) {
                    //收起 -
                    num--
                    holder.itemView.tvNum.text = num.toString()
                    MyUtil.itemSubClickAnimator(holder)
                    holder.itemView.ivAdd.setImageResource(R.drawable.add_able)
                } else if (num > 1) {
                    num--
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("数量", holder.itemView.tvNum.text.toString())
                }
        }
    }

    override fun onClick(v: View?) {
    }

    var onClick: RecommendationAdapter.OnItemClickListener? = null

    fun setOnItemClick(onItemClickListener: RecommendationAdapter.OnItemClickListener) {
        this.onClick = onItemClickListener
    }

    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int)
    }

}