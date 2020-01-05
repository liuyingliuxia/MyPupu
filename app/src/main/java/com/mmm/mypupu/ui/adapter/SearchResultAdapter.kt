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
import kotlinx.android.synthetic.main.item_recommend.view.*

class SearchResultAdapter (var list: List<Goods>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var rbClickTiems = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false)
            val holder= Holder(itemView)
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
            if (goods.mRemark.isEmpty()) {
                holder.itemView.tvRemark.visibility = View.INVISIBLE
            }
            //点击+ - 事件
            holder.itemView.ivSub.setClickable(true)
            holder.itemView.ivSub.focusable = View.FOCUSABLE
            holder.itemView.requestFocus()
            itemAddClick(holder, position)
            holder.itemView.llItemGoods.setOnClickListener { run{
                myUtil.talk(context,goods.toString())
            } }
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //一下三个方法是 控制 + - 商品的动画效果
    fun itemAddClickAnimator (holder: RecyclerView.ViewHolder){
        //+ 号旋转 用 补间动画
        val mAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.add)
        holder.itemView.ivAdd.startAnimation(mAnimation)
        // 数字部分直接平移 x轴
        holder.itemView.tvNum.x -= 68
        // - 号 旋转平移 同时进行 用 属性动画
        val mIvSub = holder.itemView.ivSub
        val objectAnimatorR : ObjectAnimator = ObjectAnimator.ofFloat(mIvSub ,"rotation",0f,180f)
        val objectAnimatorX : ObjectAnimator = ObjectAnimator.ofFloat(mIvSub ,"translationX",0f,-100f)
        val animatorSet  = AnimatorSet()
        animatorSet.playTogether(objectAnimatorR,objectAnimatorX)
        animatorSet.duration = 200
        animatorSet.start()
    }

    fun itemSubClickAnimator (holder: RecyclerView.ViewHolder){
        //+ 号旋转 用 补间动画
        val mAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim._add)
        holder.itemView.ivAdd.startAnimation(mAnimation)
        // 数字部分直接平移 x轴
        holder.itemView.tvNum.x += 68
        // - 号 旋转平移 同时进行 用 属性动画
        val mIvSub = holder.itemView.ivSub
        val objectAnimatorRSub : ObjectAnimator = ObjectAnimator.ofFloat(mIvSub ,"rotation",-180f,0f)
        val objectAnimatorTSubX : ObjectAnimator = ObjectAnimator.ofFloat(mIvSub ,"translationX",-100f,0f)
        val animatorSet  = AnimatorSet()
        animatorSet.playTogether(objectAnimatorTSubX,objectAnimatorRSub)
        animatorSet.duration = 200
        animatorSet.start()
    }

    private fun itemAddClick(holder: RecyclerView.ViewHolder, position: Int) {
        var num = 0
        val goods: Goods = list[position ]

        holder.itemView.ivAdd.setOnClickListener( object :View.OnClickListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
                Log.e("点击了：","+")
                if (num == 0) {
                    num++
                    holder.itemView.tvNum.text = num.toString()
                    itemAddClickAnimator(holder)
                }
                else  if ( num > 0 && num < goods.mNum ) {
                    num ++
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("数量", holder.itemView.tvNum.text.toString() )
                }
                else if ( num == goods.mNum){
                    // + 变成灰色
                    holder.itemView.ivAdd.setImageResource( R.drawable.add_unable )
                    holder.itemView.tvNum.text = num.toString()
                    Toast.makeText(holder.itemView.context,"无法购买更多了",Toast.LENGTH_SHORT).show()
                }

            }
        })

        holder.itemView.ivSub.setOnClickListener( object :View.OnClickListener{
            override fun onClick(v: View?) {
                if ( num ==1 ) {
                    //收起 -
                    num --
                    holder.itemView.tvNum.text = num.toString()
                    itemSubClickAnimator(holder)
                    holder.itemView.ivAdd.setImageResource( R.drawable.add_able )
                }
                else  if ( num > 1 ) {
                    num --
                    holder.itemView.tvNum.text = num.toString()
                    Log.e("数量", holder.itemView.tvNum.text.toString() )
                }
            }
        })
    }

}
