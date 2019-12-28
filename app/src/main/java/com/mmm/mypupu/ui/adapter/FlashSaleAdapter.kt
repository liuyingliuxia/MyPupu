package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.item_flash_sale.view.*
import kotlinx.android.synthetic.main.item_flash_sale_head.view.*
import kotlinx.android.synthetic.main.item_recommend.view.*
import kotlinx.android.synthetic.main.item_recommend.view.ivGoods
import kotlinx.android.synthetic.main.item_recommend.view.tvOriginalPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvQuantity
import kotlinx.android.synthetic.main.item_recommend.view.tvRemark
import kotlinx.android.synthetic.main.item_recommend.view.tvSubtitle
import kotlinx.android.synthetic.main.item_recommend.view.tvTitle
import kotlinx.android.synthetic.main.item_recommend.view.tvY2
import java.nio.channels.Selector
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class FlashSaleAdapter (var list: ArrayList<Goods>, var context: Context  ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        var maxHour = 0
        var minHour = 0
        //放在主线程 ，更新 ui
        if (TYPE_IMAGE ==  holder.itemViewType) {
            holder.itemView.tag = position
            var timeList = getNow()
            Log.e("now",timeList.toString())
            countDown(holder,timeList[2].minus(59).absoluteValue)
            val subMinu = timeList[1].minus(59).absoluteValue
            if  ( subMinu >= 0 && subMinu <= 9 )
                holder.itemView.tvMinute.setText("0"+subMinu.absoluteValue)

            else if( subMinu > 9 )
                holder.itemView.tvMinute.setText(subMinu.toString())

            when (timeList[0] > minHour && timeList[0] <= maxHour){
                 minHour == 0,maxHour == 9 -> {
                     holder.itemView.tvHour.setText("0"+8.minus(timeList[0]).absoluteValue.toString())
                 }
                minHour == 9 && maxHour == 11 ->{
                    holder.itemView.tvHour.setText("0"+10.minus(timeList[0]).absoluteValue.toString())
                }

                minHour == 11 && maxHour == 14 ->{
                    holder.itemView.tvHour.setText("0"+13.minus(timeList[0]).absoluteValue.toString())
                }
                minHour == 14 && maxHour == 16 -> {
                    holder.itemView.tvHour.setText("0"+15.minus(timeList[0]).absoluteValue.toString())
                }
                minHour == 16 && maxHour == 24 ->{
                    holder.itemView.tvHour.setText("0"+23.minus(timeList[0]).absoluteValue.toString())
                }
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
            holder.itemView.llItemFlash.setOnClickListener{
                run {
                    Toast.makeText(context,goods.toString(),Toast.LENGTH_SHORT).show()
                }
            }
            holder.itemView.tvNowRush.setOnClickListener{
                run {
                    Toast.makeText(context,"抢购成功！",Toast.LENGTH_SHORT).show()
                    holder.itemView.tvNowRush.isClickable = false
                }
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

    fun  countDown( holder: RecyclerView.ViewHolder,second : Int )   {
        object : CountDownTimer(second.times(1000).toLong(), 1000) {
            override fun onFinish() {
                val subMinu = getNow()[1].minus(59).absoluteValue

                holder.itemView.tvMinute.setText(toDouble(subMinu.minus(1)))
                countDown2(holder)
            }
            override fun onTick(millisUntilFinished: Long) {

                val sec = millisUntilFinished.div(1000)
                holder.itemView.tvSecond.setText(toDouble(sec.toInt()))
            }
        }.start()
    }

    fun  countDown2( holder: RecyclerView.ViewHolder)   {
        object : CountDownTimer(60.times(1000).toLong(), 1000) {
            override fun onFinish() {
                var min = getNow()[1]
                holder.itemView.tvMinute.setText(min.minus(1).toString())
                start()
            }
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished.div(1000)
                holder.itemView.tvSecond.setText(toDouble(sec.toInt()))
            }
        }.start()
    }


    fun getNow(): List<Int> {
     /*   if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat(" HH:mm:ss").format(Date())
        }else{*/
            var tms = Calendar.getInstance()
            //时区： 东八区，北京时间 HOUR_OF_DAY 24小时制
             tms.timeZone = TimeZone.getTimeZone("GMT+8")
            val mTimeList = arrayListOf<Int>(tms.get(Calendar.HOUR_OF_DAY),tms.get(Calendar.MINUTE),tms.get(Calendar.SECOND))
            return mTimeList
       // }

    }

    fun toDouble (time:Int ) :String  {
        if (time > 9)
            return time.toString()
        else
            return "0" + time.toString()
    }

}