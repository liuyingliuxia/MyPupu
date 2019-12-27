package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.item_flash_sale_head.view.*
import kotlinx.android.synthetic.main.item_recommend.view.*
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FlashSaleAdapter (var list: List<Goods>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if ( viewType == TYPE_IMAGE ){
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_flash_sale_head, parent,false)
            val holder1 = Holder(itemViewImg)
            return holder1
        }

        else  {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_flash_sale, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val goods: Goods = list[position ]
        //放在主线程 ，更新 ui
        if (TYPE_IMAGE ==  holder.itemViewType) {
            holder.itemView.tag = position
            val timeList = getNow()
            if (timeList[0] < 9 ){
                countDown(timeList[2])
          /*      holder.itemView.tvHour.setText( timeList[0])
                holder.itemView.tvMinute.setText(timeList[1])
                holder.itemView.tvSecond.setText(timeList[2])*/
            }
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
            if (goods.mRemark.isEmpty()){
                holder.itemView.tvRemark.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 0)
            return TYPE_IMAGE
        else
            return TYPE_GOODS

    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)




    fun  countDown( second : Int ) : String  {
        var mSecond = ""
        object : CountDownTimer(second.times(1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished.div(1000)
                if ( sec > 9 )
                    mSecond = sec.toString()
                else
                   mSecond = "0" + sec.toString()
            }
            override fun onFinish() {
                mSecond = "59"
            }

        }.start()
        return mSecond
    }



    fun getNow(): List<Int> {
     /*   if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat(" HH:mm:ss").format(Date())
        }else{*/
            var tms = Calendar.getInstance()
            val mTimeList = arrayListOf<Int>(tms.get(Calendar.HOUR_OF_DAY),tms.get(Calendar.MINUTE),tms.get(Calendar.SECOND))
            return mTimeList
       // }

    }
}